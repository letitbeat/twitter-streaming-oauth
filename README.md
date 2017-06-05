# README #

This project is a [Maven project](http://maven.apache.org/) that contains functionality that will provide you with a `com.google.api.client.http.HttpRequestFactory` that is authorised to execute calls to the Twitter API in the scope of a specific user.
You will need to provide your _Consumer Key_ and _Consumer Secret_ and follow through the OAuth process (get temporary token, retrieve access URL, authorise application, enter PIN for authenticated token).
With the resulting factory we then are able to generate and execute all necessary requests to perform the following tasks:

+ Connect to the [Twitter Streaming API](https://dev.twitter.com/streaming/overview)
	* Using the following default values:
		+ Consumer Key: `jnI6h34jDQhhYW3k3I3GyiDkO`
		+ Consumer Secret: `R3DnDX4KAj0uveG68VxhSu0lYLnmkcukWMTMtxinPJM9TYe0me`
	* The app name will be `streaming-oauth-test`
	* You will need to login with Twitter
+ Filter messages given as execution arguments more info in the [How to build and run](#How to build and run) section.
+ Retrieve the incoming messages for 30 seconds or up to 100 messages, whichever comes first. We use this default values, but you are free to change in [configuration.properties](/src/main/java/resources/configuration.properties) file.
+ For each message, the following information is stored:
	* The message ID
	* The creation date of the message as epoch value
	* The text of the message
	* The author of the message
+ And for each author the following:
	* The user ID
	* The creation date of the user as epoch value
	* The name of the user
	* The screen name of the user
+ The application returns the messages grouped by user (users sorted chronologically, ascending)
+ The messages per user are also sorted chronologically, ascending

## How to build and run

#### Using maven

+ Run the following command to compile and install dependencies:
  
  `$ mvn install`
  
+ Once compiled you can run start tracking messages with the default "test" term.

  `$ mvn exec:java`
  
  Additionally, you can add more track terms as arguments separated by comma.
  
  `$ mvn exec:java -Dexec.args=term1,term2,term3...`

#### Using java -jar

+ Run the following command to compile and install dependencies:
     
   `$ mvn install`
+ Execute the generated .jar file

   `$ java -jar target/oauth-twitter-0.0.1-SNAPSHOT.jar term1,term2,term3...`