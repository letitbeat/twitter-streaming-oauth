package org.letitbeat.oauth.app;

import org.letitbeat.oauth.twitter.TwitterAuthenticator;
import org.letitbeat.oauth.twitter.TwitterClient;
import org.letitbeat.oauth.twitter.TwitterMessageRetrieverWorker;
import org.letitbeat.oauth.twitter.configuration.TwitterConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point application which invokes the {@link TwitterMessageRetrieverWorker}
 * in order to start tracking the messages from Twitter Streaming API.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class EntryPoint {

	private static final Logger logger = Logger
			.getLogger(EntryPoint.class.getName());

	public static void main(String[] args) {

		TwitterConfiguration configuration;
		TwitterMessageRetrieverWorker worker;
		TwitterAuthenticator twitterAuthenticator;
		TwitterClient client;
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		try {
			configuration = new TwitterConfiguration();

			twitterAuthenticator = new TwitterAuthenticator(
					System.out, configuration.getConsumerKey(),
					configuration.getConsumerSecret());

			client = new TwitterClient(twitterAuthenticator);

			worker = new TwitterMessageRetrieverWorker(configuration,
					client, getTrackTerms(args));

			logger.info("Starting retriever worker...");
			executorService.submit(worker);
		} catch (Exception e) {
			logger.log(Level.SEVERE,
					"An error occurred during retriever execution");
		} finally {
			executorService.shutdown();
		}

	}

	private static List<String> getTrackTerms(String[] args) {

		List<String> trackTerms = new ArrayList<>();

		String inputTerms = (args.length > 0) ? args[0] : "";

		for (String term : inputTerms.split(",")) {
			if (!term.isEmpty()) {
				trackTerms.add(term);
			}
		}

		if (trackTerms.size() == 0) {
			trackTerms.add("test");
		}

		return trackTerms;
	}

}
