package org.letitbeat.oauth.twitter;

import org.junit.Before;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Tests methods for {@link TwitterClient} class.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterClientTest {

	private TwitterClient client;
	private TwitterAuthenticator auth;

	@Before
	public void setUp(){
		auth = mock(TwitterAuthenticator.class);
		client = new TwitterClient(auth);
	}

	@Test
	public void testGetUrlNoParameters(){

		String url = client.getUrl();

		assertEquals("https://stream.twitter.com/1.1/statuses/filter.json?", url);
	}

	@Test
	public void testGetUrl1Parameter() throws UnsupportedEncodingException {

		client.addQueryParameter(StreamingParameters.TRACK, "termToTrack");

		String queryParameters = client.getQueryParameters();

		assertTrue(queryParameters.contains(StreamingParameters.TRACK));
	}

	@Test public void testEncode() throws UnsupportedEncodingException {

		assertEquals("%2Fvalue+to+encode", client.encode("/value to encode"));
		assertEquals("second%2F+value+%22to%22+encode",
				client.encode("second/ value \"to\" encode"));
	}
}
