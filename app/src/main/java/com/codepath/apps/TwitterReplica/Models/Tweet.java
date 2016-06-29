package com.codepath.apps.TwitterReplica.Models;

import android.text.format.DateUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by gegbo on 6/27/16.
 */

@Parcel
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

    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //Deserialize the JSON and build Tweet objects
    public static Tweet fromJSON(JSONObject jsonObject) {
         Tweet tweet = new Tweet();
        try {
            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = getRelativeTimeAgo(jsonObject.getString("created_at"));
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

    // getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
    public static String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);

        String relativeDate = "";
        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(relativeDate.contains("seconds") || relativeDate.contains("second")) {
            return relativeDate.substring(0,2) + "s";
        }
        else if(relativeDate.contains("minutes") || relativeDate.contains("minute")) {
            return relativeDate.substring(0,2) + "m";
        }
        else if(relativeDate.contains("hours") || relativeDate.contains("hour")) {
            return relativeDate.substring(0,2) + "h";
        }
        else if(relativeDate.contains("Yesterday")) {
            return "1d";
        }
        else if(relativeDate.contains("days") || relativeDate.contains("day")) {
            return relativeDate.substring(0,1) + "d";
        }
        else if(relativeDate.contains("weeks") || relativeDate.contains("week")) {
            return relativeDate.substring(0,1) + "w";
        }
        else if(relativeDate.contains("months") || relativeDate.contains("month")) {
            return relativeDate.substring(0,2) + "m";
        }
        else if(relativeDate.contains("years") || relativeDate.contains("year")) {
            return relativeDate.substring(0,2) + "y";
        }
        return relativeDate;
    }
}
