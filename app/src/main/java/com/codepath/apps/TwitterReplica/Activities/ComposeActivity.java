package com.codepath.apps.TwitterReplica.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.codepath.apps.TwitterReplica.Models.Tweet;
import com.codepath.apps.TwitterReplica.R;
import com.codepath.apps.TwitterReplica.TwitterApplication;
import com.codepath.apps.TwitterReplica.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ComposeActivity extends AppCompatActivity {

    EditText etMessage;
    TwitterClient client;
    Menu menu;
    MenuItem miCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);

        etMessage = (EditText) findViewById(R.id.etTweet);
        client = TwitterApplication.getRestClient();

        final TextWatcher TextEditorWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int remaining = 140 - s.length();
                miCount.setTitle(String.valueOf(remaining));
            }

            public void afterTextChanged(Editable s) {
            }
        };
        etMessage.addTextChangedListener(TextEditorWatcher);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_compose,menu);
        this.menu = menu;
        miCount = menu.findItem(R.id.micount);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onTweet(MenuItem item) {
        client.postTweet(etMessage.getText().toString(),new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Tweet tweet = Tweet.fromJSON(response);

                Intent i = new Intent();
                i.putExtra("tweet", Parcels.wrap(tweet));
                setResult(RESULT_OK,i);

                finish();
            }
        });
    }

}
