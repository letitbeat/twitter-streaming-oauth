package org.letitbeat.oauth.twitter.configuration;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test methods of the {@link TwitterConfiguration} class.
 *
 * @author Jose Reyes
 * @version 1.0 05/2017.
 */
public class TwitterConfigurationTest {

	@Test
	public void testNewInstance(){
		TwitterConfiguration config = new TwitterConfiguration();

		assertEquals("vp8qXAMoZzy6jowJdtouPLUUb", config.getConsumerKey());
		assertEquals("IMx3eIRfXXbRimoIz7cNpZCl0dr9dYEdRuDVTr2C4LdResXjN7",
				config.getConsumerSecret());
	}

	@Test
	public void testDefaults(){
		TwitterConfiguration config = new TwitterConfiguration();
		assertEquals(100, config.getMaxMessages());
		assertEquals(30, config.getTimeout());
	}
}
