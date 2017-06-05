package org.letitbeat.oauth.twitter;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.util.Maps;
import com.google.common.base.Joiner;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Twitter Client which performs the requests to the Twitter Streaming API
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterClient {

	private static final String DEFAULT_URL_ENCODING = "UTF-8";

	private static final String BASE_STREAM_URL = "https://stream.twitter.com/1.1/statuses/filter.json?";

	private final HashMap<String, String> queryParameters = Maps.newHashMap();

	private TwitterAuthenticator authenticator;

	private HttpResponse response;

	public TwitterClient(TwitterAuthenticator authenticator) {
		this.authenticator = authenticator;
	}

	public HttpResponse connect()
			throws IOException, TwitterAuthenticationException {

		addDefaultQueryParameters();

		HttpRequestFactory requestFactory = this.authenticator
				.getAuthorizedHttpRequestFactory();

		HttpRequest request = requestFactory
				.buildGetRequest(new GenericUrl(getUrl()));

		response = request.execute();

		return response;
	}

	public String getUrl() {
		return BASE_STREAM_URL + getQueryParameters();
	}

	public void addQueryParameter(String param, String value)
			throws UnsupportedEncodingException {
		queryParameters.put(encode(param), encode(value));
	}

	public String getQueryParameters() {
		return Joiner
				.on("&")
				.withKeyValueSeparator("=")
				.join(queryParameters);
	}

	public String encode(String value) throws UnsupportedEncodingException {
		return URLEncoder.encode(value, DEFAULT_URL_ENCODING);
	}

	public void disconnect() throws IOException {
		if (this.response != null) {
			this.response.disconnect();
		}
	}

	private void addDefaultQueryParameters()
			throws UnsupportedEncodingException {
		addQueryParameter(StreamingParameters.DELIMITED,
				StreamingParameters.Delimiters.Newline.getValue());
	}

}
