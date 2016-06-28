package com.codepath.apps.TwitterReplica.Models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by gegbo on 6/27/16.
 */
public class Tweet {

    private String body;
    private long uid; //unique id for the tweet
    private User user;
    private String createdAt;


    public User getUser() {
        return user;
    }

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    //Deserialize the JSON and build Tweet objects
    private static Tweet fromJSON(JSONObject jsonObject) {
         Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return tweet;
    }

    public static ArrayList<Tweet> fromJSONArray(JSONArray array) {
        ArrayList<Tweet> tweets = new ArrayList<>();

        for(int i = 0; i<array.length(); i++) {
            try {
                    JSONObject jsonTweet = array.getJSONObject(i);
                    Tweet tweet = Tweet.fromJSON(jsonTweet);
                     if(tweet != null) {
                         tweets.add(tweet);
                     }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return tweets;
    }
}
