package org.letitbeat.oauth.twitter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

/**
 * Basic representation of a Tweet.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Tweet extends BaseTwitterObject {

	/**
	 * Tweet unique identifier
	 */
	private long id;

	/**
	 * Tweet in plain text
	 */
	private String text;

	/**
	 * Creation date of the Tweet
	 */
	@JsonProperty(value = "created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM d HH:mm:ss Z y")
	private Date createdAt;

	/**
	 * The {@link User} which is associated with the current tweet.
	 */
	private User user;

	public User getUser() {
		return user;
	}

	public Tweet setUser(User user) {
		this.user = user;
		return this;
	}

	public long getId() {
		return id;
	}

	public Tweet setId(long id) {
		this.id = id;
		return this;
	}

	public String getText() {
		return text;
	}

	public Tweet setText(String text) {
		this.text = text;
		return this;
	}

	public Tweet setCreatedAt(Date createdAt){
		this.createdAt = createdAt;
		return this;
	}

	public Date getCreatedAt(){
		return createdAt;
	}

	@Override
	public String toString(){
		StringBuilder builder =  new StringBuilder();
		builder.append("----------------------------------")
				.append(System.lineSeparator())
				.append(" - Message ID - ")
				.append(this.id)
				.append(System.lineSeparator())
				.append(" - Text - ")
				.append(this.text)
				.append(System.lineSeparator())
				.append(" - Created At - ")
				.append(this.createdAt.toString())
				.append(System.lineSeparator())
				.append(" - User - @")
				.append(this.user.getScreenName())
				.append(System.lineSeparator())
				.append("---------------------------------")
				.append(System.lineSeparator());

		return builder.toString();
	}

	@Override
	public boolean equals(Object o){
		if(o == this) return true;

		if(!(o instanceof Tweet)){
			return false;
		}

		Tweet tweet = (Tweet)o;
		return Objects.equals(id, tweet.getId());
	}

	@Override
	public int hashCode(){
		return Objects.hash(id);
	}

}
