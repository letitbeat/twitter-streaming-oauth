package org.letitbeat.oauth.twitter;

import com.google.api.client.http.*;
import com.google.api.client.json.Json;
import com.google.api.client.testing.http.HttpTesting;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.letitbeat.oauth.twitter.configuration.TwitterConfiguration;
import org.letitbeat.oauth.twitter.domain.Tweet;
import org.letitbeat.oauth.twitter.domain.User;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Test methods of the {@link TwitterMessageRetrieverWorker} class.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterMessageRetrieverWorkerTest {

	private TwitterMessageRetrieverWorker worker;
	private TwitterClient client;

	@Before
	public void setUp(){
		TwitterConfiguration config = new TwitterConfiguration();
		client = mock(TwitterClient.class);

		List<String> terms = new ArrayList<>();
		terms.add("one");

		worker	= new TwitterMessageRetrieverWorker(config, client, terms);

		assertNotNull(worker);
		assertEquals(0, worker.getTweetsByUser().size());
	}

	@Test
	public void testRunWorker()
			throws IOException, TwitterAuthenticationException,
			InterruptedException {

		HttpTransport transport = new MockHttpTransport() {
			public LowLevelHttpRequest buildRequest(String method, String url)
					throws IOException {
				return new MockLowLevelHttpRequest() {
					@Override public LowLevelHttpResponse execute()
							throws IOException {
						MockLowLevelHttpResponse response = new MockLowLevelHttpResponse();
						response.setStatusCode(200);
						response.setContentType(Json.MEDIA_TYPE);
						response.setContent(
								"{\"id\" : 123456, \"text\" : \"Message for testing\", \"created_at\" : \"Fri May 26 10:00:00 -0800 2017\", \"user\" : {\"id\" : 7891011,\"name\" : \"name\",\"screen_name\" : \"screenName\",\"created_at\" : \"Fri May 26 09:00:00 -0800 2017\"}}");
						return response;
					}
				};
			}
		};

		HttpRequest request = transport.createRequestFactory().buildGetRequest(
				HttpTesting.SIMPLE_GENERIC_URL);
		HttpResponse response = request.execute();

		when(client.connect()).thenReturn(response);
		doNothing().when(client).addQueryParameter(anyString(), anyString());

		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.submit(worker);

		executorService.awaitTermination(2, TimeUnit.SECONDS);

		SortedMap<User, Set<Tweet>> tweetsByUser = worker.getTweetsByUser();

		assertEquals(1, tweetsByUser.size());
		assertEquals(1, tweetsByUser.get(tweetsByUser.firstKey()).size());
		assertEquals(7891011L, tweetsByUser.firstKey().getId());
		assertEquals("screenName", tweetsByUser.firstKey().getScreenName());
	}

}
