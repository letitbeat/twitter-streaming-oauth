package org.letitbeat.oauth.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.repackaged.com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.letitbeat.oauth.twitter.configuration.TwitterConfiguration;
import org.letitbeat.oauth.twitter.domain.Tweet;
import org.letitbeat.oauth.twitter.domain.User;
import org.letitbeat.oauth.twitter.domain.comparators.DateComparator;

import java.io.*;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main worker which performs the task of retrieving the messages
 * from Twitter Streaming API.
 *
 * Retrieved {@link Tweet}s are grouped by {@link User} and sorted ascending.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterMessageRetrieverWorker implements Runnable {

	private static final Logger logger = Logger.getLogger(TwitterMessageRetrieverWorker.class.getName());

	private TwitterConfiguration configuration;
	private TwitterClient client;
	private SortedMap<User, Set<Tweet>> tweetsByUser =  Maps
			.newTreeMap(new DateComparator());
	private List<String> terms;

	public TwitterMessageRetrieverWorker(TwitterConfiguration configuration,
			TwitterClient client, List<String> terms){

		this.configuration = configuration;
		this.client = client;
		this.terms = terms;

	}

	@Override
	public void run() {

		try {
			String trackTerms = Joiner.on(",").join(terms);
			client.addQueryParameter(StreamingParameters.TRACK, trackTerms);

			logger.info(String.format(
					"Started retrieving messages with the following terms: %s",
					trackTerms));

			HttpResponse response = client.connect();

			processResponse(response.getContent());

			printOutput();

			logger.info("Finished retrieving messages.");

		} catch (UnsupportedEncodingException e) {
			logger.log(Level.SEVERE,
					"Error adding arguments to client: " + e.getMessage(), e);
		} catch (IOException e) {
			logger.log(Level.SEVERE,
					"Error establishing connection: " + e.getMessage(), e);
		} catch (TwitterAuthenticationException e) {
			logger.log(Level.SEVERE,
					"Error authenticating with Twitter API: " + e.getMessage(),
					e);
		} finally {
			if (client != null) {
				try {
					client.disconnect();
				} catch (IOException e) {
					logger.log(Level.SEVERE,
							"Error disconnecting client: " + e.getMessage(), e);
				}

			}
		}

	}

	public SortedMap<User, Set<Tweet>> getTweetsByUser(){
		return tweetsByUser;
	}

	private void processResponse(InputStream content) throws IOException {

		InputStreamReader input;
		BufferedReader reader;
		ObjectMapper mapper;
		int count = 0;

		try {

			mapper = new ObjectMapper();
			input = new InputStreamReader(content, Charset.defaultCharset());
			reader = new BufferedReader(input);

			long deadline = System.currentTimeMillis()
					+ configuration.getTimeout() * 1000;
			String message;

			while ((message = reader.readLine()) != null) {
				Tweet tweet = mapper.readValue(message, Tweet.class);

				addTweet(tweet);

				if (++count == configuration.getMaxMessages()
						|| System.currentTimeMillis() > deadline) {

					logger.info(String.format(
							"Maximum number of messages: %d or timeout of: %d "
									+ "seconds reached, exiting worker.",
							configuration.getMaxMessages(),
							configuration.getTimeout()));
					break;
				}
				logger.info(String.format("Processed %d messages...", count));
			}
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error processing response : ",
					e.getMessage());
			throw new RuntimeException(
					"Error processing response from Twitter API");
		}

		logger.info(String.format("Successfully retrieved %d messages", count));
	}

	private void addTweet(Tweet tweet) {
		Set<Tweet> tweets;
		if (tweetsByUser.containsKey(tweet.getUser())) {
			tweets = tweetsByUser.get(tweet.getUser());
		} else {
			tweets = new TreeSet<>(new DateComparator());
		}
		tweets.add(tweet);
		tweetsByUser.put(tweet.getUser(), tweets);
	}

	private void printOutput() {
		for (Map.Entry<User, Set<Tweet>> entry : tweetsByUser.entrySet()) {
			System.out.println(
					"Tweets for user: @" + entry.getKey().getScreenName());
			for (Tweet tweet : entry.getValue()) {
				System.out.println(tweet.toString());
			}
		}
	}

}
