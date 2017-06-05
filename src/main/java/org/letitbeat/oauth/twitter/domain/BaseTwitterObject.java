package org.letitbeat.oauth.twitter.domain;

import java.util.Date;

/**
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class BaseTwitterObject {

	private Date createdAt;

	public Date getCreatedAt() {
		return createdAt;
	}

	public BaseTwitterObject setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
		return this;
	}

}
