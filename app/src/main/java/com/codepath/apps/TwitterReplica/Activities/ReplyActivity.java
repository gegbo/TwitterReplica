package com.codepath.apps.TwitterReplica.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.codepath.apps.TwitterReplica.Models.Tweet;
import com.codepath.apps.TwitterReplica.R;

import org.parceler.Parcels;

public class ReplyActivity extends AppCompatActivity {

    EditText etReply;
    Tweet tweet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);

        etReply = (EditText) findViewById(R.id.etReply);
        tweet = Parcels.unwrap(getIntent().getParcelableExtra("tweet"));
        etReply.setText("@"+tweet.getUser().getScreenName()+":");
    }

    @Override 
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_reply,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onTweet(MenuItem item) {
    }
}
