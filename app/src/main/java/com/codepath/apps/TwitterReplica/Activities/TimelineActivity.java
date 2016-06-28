 package com.codepath.apps.TwitterReplica.Activities;

 import android.content.Intent;
 import android.os.Bundle;
 import android.support.v4.app.Fragment;
 import android.support.v4.app.FragmentManager;
 import android.support.v4.app.FragmentPagerAdapter;
 import android.support.v4.view.ViewPager;
 import android.support.v7.app.AppCompatActivity;
 import android.view.Menu;
 import android.view.MenuItem;

 import com.astuetz.PagerSlidingTabStrip;
 import com.codepath.apps.TwitterReplica.Fragments.HomeTimelineFragment;
 import com.codepath.apps.TwitterReplica.Fragments.MentionsTimelineFragment;
 import com.codepath.apps.TwitterReplica.R;

 public class TimelineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        //Get the viewpager
        ViewPager vpPager = (ViewPager)findViewById(R.id.viewpager);
        //Set the view pager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
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
         startActivity(i);
     }

     //return the order of the fragments in the view pager
     public class TweetsPagerAdapter extends FragmentPagerAdapter {
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
