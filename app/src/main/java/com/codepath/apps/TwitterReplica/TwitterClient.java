package com.codepath.apps.TwitterReplica;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class  TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
	public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
	public static final String REST_CONSUMER_KEY = "kcDCXq6chru1LDR0xnnRsIkSq";       // Change this
	public static final String REST_CONSUMER_SECRET = "nLwQGmWnX3G9MIJHJxyeCCkiV1XSUm4m80u0dSbaFqOYqp8nE8"; // Change this
	public static final String REST_CALLBACK_URL = "oauth://cptwittereplica"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// CHANGE THIS
	// DEFINE METHODS for different API endpoints here
	public void getInterestingnessList(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("?nojsoncallback=1&method=flickr.interestingness.getList");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("format", "json");
		client.get(apiUrl, params, handler);
	}

	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */

	public void getHomeTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//Specify params

		RequestParams params = new RequestParams();
		params.put("count",25);

		//Execute request
		getClient().get(apiUrl,params,handler);
	}

	public void getMentionsTimeline(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/mentions_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count",25);

		//Execute request
		getClient().get(apiUrl,params,handler);
	}

	public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/user_timeline.json");

		RequestParams params = new RequestParams();
		params.put("count",25);
		params.put("screen_name",screenName);

		//Execute request
		getClient().get(apiUrl,params,handler);

	}

	public void verifyUserInfo(AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("account/verify_credentials.json");
		getClient().get(apiUrl,null,handler);
	}

	public void postTweet(String tweet, long replyid,boolean haveReply, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/update.json");

		RequestParams params = new RequestParams();
		params.put("status",tweet);

		if(haveReply == true) {
			params.put("in_reply_to_status_id",replyid);
		}

		getClient().post(apiUrl,params,handler);
	}

	public void retweet(long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/retweet/"+uid+".json");

		getClient().post(apiUrl,null,handler);
	}

	public void unretweet(long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("statuses/unretweet/"+uid+".json");

		getClient().post(apiUrl,null,handler);
	}

	public void favorite(long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/create.json");

		RequestParams params = new RequestParams();
		params.put("id",uid);

		getClient().post(apiUrl,params,handler);
	}

	public void unfavorite(long uid, AsyncHttpResponseHandler handler) {
		String apiUrl = getApiUrl("favorites/destroy.json");

		RequestParams params = new RequestParams();
		params.put("id",uid);

		getClient().post(apiUrl,params,handler);
	}
}