package org.letitbeat.oauth.twitter;

/**
 * Twitter Streaming API Parameters.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class StreamingParameters {

	public static final String DELIMITED = "delimited";
	public static final String TRACK = "track";

	public enum Delimiters{

		Newline("newline"),
		Length("length");

		private String value;

		Delimiters(String value){
			this.value = value;
		}

		public String getValue(){
			return this.value;
		}
	}
}
