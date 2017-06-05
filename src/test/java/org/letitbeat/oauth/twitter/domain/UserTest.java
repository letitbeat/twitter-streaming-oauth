package org.letitbeat.oauth.twitter.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.letitbeat.oauth.twitter.domain.comparators.DateComparator;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class UserTest {

	private static final String DATE_FORMAT_PATTERN = "E MMM d HH:mm:ss Z y";
	@Test
	public void testNewInstance(){

		long now = System.currentTimeMillis();

		User user = new User()
				.setId(12345)
				.setName("new user")
				.setScreenName("screen")
				.setCreatedAt(new Date(now));

		assertEquals(12345L, user.getId());
		assertEquals("new user", user.getName());
		assertEquals("screen", user.getScreenName());
	}

	@Test
	public void testJsonToObject() throws IOException, ParseException {
		InputStream jsonStream = getClass().getClassLoader()
				.getResourceAsStream("user.json");

		ObjectMapper mapper = new ObjectMapper();

		User mappedUser = mapper.readValue(jsonStream, User.class);

		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);

		assertEquals(7891011L, mappedUser.getId());
		assertEquals("This should be the name", mappedUser.getName());
		assertEquals("screenName", mappedUser.getScreenName());
		assertEquals(dateFormat.parse("Fri May 26 09:00:00 -0800 2017"), mappedUser.getCreatedAt());
	}

	@Test
	public void testEquals(){

		User user1 = new User().setId(12345L);
		User user2 = new User().setId(12345L);

		assertEquals(user1, user2);
	}

	@Test
	public void testDateComparator() throws ParseException {

		DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN);
		User user1 = new User().setId(12345L).setCreatedAt(
				dateFormat.parse("Fri May 26 09:00:00 -0800 2017"));

		User user2 = new User().setId(678910L).setCreatedAt(
				dateFormat.parse("Fri May 26 08:00:00 -0800 2017"));

		User user3 = new User().setId(111213L).setCreatedAt(
				dateFormat.parse("Fri May 26 07:00:00 -0800 2017"));

		List<User> users = new ArrayList<>();

		users.add(user1);
		users.add(user2);
		users.add(user3);

		Collections.sort(users, new DateComparator());

		assertEquals(user3, users.get(0));
		assertEquals(user2, users.get(1));
		assertEquals(user1, users.get(2));
	}
}
