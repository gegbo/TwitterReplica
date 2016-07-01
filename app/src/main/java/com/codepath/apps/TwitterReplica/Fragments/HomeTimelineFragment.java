package com.codepath.apps.TwitterReplica.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.codepath.apps.TwitterReplica.Models.Tweet;
import com.codepath.apps.TwitterReplica.TwitterApplication;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by gegbo on 6/27/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeline();

    }


    //Send out an API request to get the timeline json
    //Fill the listview by creating the tweet objects from the json
    public void populateTimeline() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //SUCCESS

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                addAll(Tweet.fromJSONArray(response));
                swipeContainer.setRefreshing(false);
            }

            //FAILURE
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("ERROR",errorResponse.toString());
            }


        });
    }

    public void appendTweet(Tweet tweet) {
        tweets.add(0,tweet);
        aTweets.notifyDataSetChanged();
        lvTweets.setSelection(0);
    }

    @Override
    protected void refreshTweets() {
        populateTimeline();
    }
}
