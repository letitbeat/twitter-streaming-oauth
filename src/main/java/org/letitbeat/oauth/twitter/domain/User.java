package org.letitbeat.oauth.twitter.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.Objects;

/**
 * Basic representation of a Twitter User.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends BaseTwitterObject {

	/**
	 * User unique identifier
	 */
	private long id;

	/**
	 * User Name
	 */
	private String name;

	/**
	 * User Screen Name
	 */
	@JsonProperty(value = "screen_name")
	private String screenName;

	/**
	 * The creation date of this User
	 */
	@JsonProperty(value = "created_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "E MMM d HH:mm:ss Z y")
	private Date createdAt;

	public long getId() {
		return id;
	}

	public User setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public User setName(String name) {
		this.name = name;
		return this;
	}

	public String getScreenName() {
		return screenName;
	}

	public User setScreenName(String screenName) {
		this.screenName = screenName;
		return this;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public User setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (o == this)
			return true;
		if (!(o instanceof User)) {
			return false;
		}

		User user = (User) o;

		return Objects.equals(this.id, user.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.id);
	}

}
