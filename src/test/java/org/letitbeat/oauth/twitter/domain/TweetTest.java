package org.letitbeat.oauth.twitter.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TweetTest {

	@Test public void testNewInstance() {

		long now = System.currentTimeMillis();

		Tweet tweet = new Tweet().setText("test").setCreatedAt(new Date(now));

		assertEquals(0, tweet.getId());
		assertEquals("test", tweet.getText());
		assertEquals(new Date(now), tweet.getCreatedAt());
	}

	@Test public void testJsonToObject() throws IOException {

		InputStream jsonStream = getClass().getClassLoader()
				.getResourceAsStream("tweet.json");

		ObjectMapper mapper = new ObjectMapper();

		Tweet mappedTweet = mapper.readValue(jsonStream, Tweet.class);

		assertEquals(123456L, mappedTweet.getId());
		assertEquals("This is a tweet message for testing purposes",
				mappedTweet.getText());
		assertEquals(7891011L, mappedTweet.getUser().getId());

	}

	@Test
	public void testEquals() {
		Tweet tweet1 = new Tweet().setId(12345L);
		Tweet tweet2 = new Tweet().setId(12345L);

		assertEquals(tweet1, tweet2);
	}


}
