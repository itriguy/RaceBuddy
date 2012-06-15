package com.xmatters.android.racebuddy.twitter;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class will handle the interface with Twitter
 */
public class Tweeter {

    private final static String twitterConsumerKey = "YIhcm75h0gPOWw0Ri9CI5w";
    private final static String twitterConsumerSecret = "dFubsJNgc3ai84PMCe7wlJCFPFtdkb94nldvRtA6MM";
    //temp - should be stored in config settings - diff for each user
    private final static String twitterAccessToken = "94205888-eFjNlhLOVU2ETxzwopDxHkmYc2DwWWLr9wKBGuyrc";
    private final static String twitterAccessTokenSecret = "bUrWM9ajaHZBJi3uQGKDsiikBPWzCirXzMCMEUoJj4";

    private Twitter twitter;

    public Tweeter() {
        //NOTE: This is hard coded to use my twitter account. Remove when ready share code.
        config(twitterAccessToken, twitterAccessTokenSecret);
    }

    public Tweeter(String userAccessToken, String userAccessTokenSecret) {
        config(userAccessToken, userAccessTokenSecret);
    }

    protected void config(String accessToken, String accessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(twitterConsumerKey)
            .setOAuthConsumerSecret(twitterConsumerSecret)
            .setOAuthAccessToken(twitterAccessToken)
            .setOAuthAccessTokenSecret(twitterAccessTokenSecret);
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter = twitterFactory.getInstance();
    }

    public String getAuthorizationURL() throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
            .setOAuthConsumerKey(twitterConsumerKey)
            .setOAuthConsumerSecret(twitterConsumerSecret);
        TwitterFactory twitterFactory = new TwitterFactory(cb.build());
        twitter = twitterFactory.getInstance();
        return twitter.getOAuthRequestToken().getAuthorizationURL();
    }

    public String tweet(String message) {
        try {
            if(! twitter.getAuthorization().isEnabled()) {
                return "OAuth consumer key/secret is not set.";
            }
            Status status = twitter.updateStatus(message);
            return "Successfully updated the status to [" + status.getText() + "].";
        }
        catch(TwitterException te) {
            te.printStackTrace();
            return "Failed to get timeline: " + te.getMessage();
        }
    }
}
