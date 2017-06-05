package org.letitbeat.oauth.twitter.configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Used to hold configuration data across the application.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterConfiguration {

	private Properties properties;

	/**
	 * Twitter OAuth Consumer key
	 **/
	private String consumerKey;

	/**
	 * Twitter OAuth Consumer secret
	 **/
	private String consumerSecret;

	/**
	 * Maximum number of message to retrieve
	 **/
	private int maxMessages;

	/**
	 * The number in seconds that should retrieve messages
	 **/
	private long timeout;

	/**
	 * Creates a new instance of {@link TwitterConfiguration}
	 */
	public TwitterConfiguration() {
		loadProperties();
	}

	/**
	 * Gets the {@link #consumerKey} configuration property.
	 *
	 * @return The Twitter consumer key
	 */
	public String getConsumerKey() {
		return consumerKey;
	}

	public TwitterConfiguration setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
		return this;
	}

	/**
	 * Gets the {@link #consumerSecret} configuration property.
	 * @return
	 */
	public String getConsumerSecret() {
		return consumerSecret;
	}

	public TwitterConfiguration setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
		return this;
	}

	public int getMaxMessages() {
		return maxMessages == 0 ? 100 : maxMessages;
	}

	public TwitterConfiguration setMaxMessages(int maxMessages) {
		this.maxMessages = maxMessages;
		return this;
	}

	public long getTimeout() {
		return timeout == 0 ? 30 : timeout;
	}

	public TwitterConfiguration setTimeout(long timeout) {
		this.timeout = timeout;
		return this;
	}

	/**
	 * Should read the properties from {#code configuration.properties}
	 * and defined in this class.
	 *
	 * Properties which could be optional, should include a default value.
	 *
	 */
	private void loadProperties() {
		this.properties = new Properties();
		final InputStream stream = TwitterConfiguration.class.
				getResourceAsStream("/configuration.properties");

		if (stream == null) {
			throw new RuntimeException(
					"No configuration.properties file present.");
		}

		try {
			this.properties.load(stream);

			this.consumerKey = this.properties.getProperty("consumer.key");

			this.consumerSecret = this.properties
					.getProperty("consumer.secret");

			if (this.properties.getProperty("max.messages") != null) {
				this.maxMessages = Integer
						.parseInt(this.properties.getProperty("max.messages"));
			}
			if (this.properties.getProperty("timeout") != null) {
				this.timeout = Long
						.parseLong(this.properties.getProperty("timeout"));
			}

		} catch (IOException e) {
			throw new RuntimeException(
					"Configuration could no be loaded " + e.getMessage());
		}
	}
}
