 package com.codepath.apps.TwitterReplica.Activities;

 import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.TwitterReplica.Adapters.SmartFragmentStatePagerAdapter;
import com.codepath.apps.TwitterReplica.Fragments.HomeTimelineFragment;
import com.codepath.apps.TwitterReplica.Fragments.MentionsTimelineFragment;
import com.codepath.apps.TwitterReplica.Models.Tweet;
import com.codepath.apps.TwitterReplica.R;

import org.parceler.Parcels;

 public class TimelineActivity extends AppCompatActivity {

     public final int COMPOSE_REQUEST_CODE  = 500;
     ViewPager vpPager;
     TweetsPagerAdapter tpa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Get the viewpager
         vpPager = (ViewPager)findViewById(R.id.viewpager);
        //Set the view pager adapter for the pager
        tpa = new TweetsPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(tpa);
        //Find the sliding tabstrip
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip)findViewById(R.id.tabs);
        //Attach the tab strip to the viewpager
        tabStrip.setViewPager(vpPager);

    }

     @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         getMenuInflater().inflate(R.menu.menu_timeline,menu);
         return true;
     }

     @Override
     public boolean onOptionsItemSelected(MenuItem item) {
         return super.onOptionsItemSelected(item);
     }

     public void onProfileView(MenuItem mi) {
         Intent i = new Intent(this,ProfileActivity.class);
         i.putExtra("code",10);
         startActivity(i);
     }

     public void onComposeView(MenuItem item) {
         Intent i = new Intent(this,ComposeActivity.class);
         startActivityForResult(i,COMPOSE_REQUEST_CODE);
     }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {

         if(resultCode == RESULT_OK && requestCode == COMPOSE_REQUEST_CODE) {

             HomeTimelineFragment fragmentHomeTweets =
                     (HomeTimelineFragment) tpa.getRegisteredFragment(0);

             Tweet tweet = Parcels.unwrap(data.getParcelableExtra("tweet"));

             fragmentHomeTweets.appendTweet(tweet);

         }
     }


     //return the order of the fragments in the view pager
     public class TweetsPagerAdapter extends SmartFragmentStatePagerAdapter {
         private String tabTitles[] = {"Home","Mentions"};

         //Adapter gets the manager to insert or remove fragment from activity
         public TweetsPagerAdapter(FragmentManager fm) {
             super(fm);
         }

         //Controls the order and creation of fragments within the activity
         @Override
         public Fragment getItem(int position) {
             if(position == 0) {
                 return new HomeTimelineFragment();
             }
             else if (position == 1) {
                 return new MentionsTimelineFragment();
             }
             else {
                 return null;
             }
         }

         //Returns the tab title
         @Override
         public CharSequence getPageTitle(int position) {
             return tabTitles[position];
         }

         //How many fragments are there to switch between?
         @Override
         public int getCount() {
             return tabTitles.length;
         }
     }

}
