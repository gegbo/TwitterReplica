package com.codepath.apps.TwitterReplica.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.TwitterReplica.Activities.ProfileActivity;
import com.codepath.apps.TwitterReplica.Models.Tweet;
import com.codepath.apps.TwitterReplica.Models.User;
import com.codepath.apps.TwitterReplica.R;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by gegbo on 6/27/16.
 */

//Taking the tweet objects and displaying them into views displayed in the list
public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    Context context;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, 0,tweets);
        this.context=context;
    }

    //Implement the viewholder pattern
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1. Get the tweet
        Tweet tweet = getItem(position);
        //2. Find or inflate the template
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet,parent,false);
        }
        //3. Find the subviews to to fill with data in the template
        final ImageView ivProfileImage = (ImageView)convertView.findViewById(R.id.ivProfileImage);
        TextView tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvTime = (TextView) convertView.findViewById(R.id.tvTime);
        //4. Populate data into the subviews
        tvUsername.setText(tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvTime.setText(tweet.getCreatedAt());
        ivProfileImage.setImageResource(0);
        ivProfileImage.setTag(R.string.user, tweet.getUser());
        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);

        ivProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(context, ProfileActivity.class);
                User user = (User)ivProfileImage.getTag(R.string.user);
                i.putExtra("code",15);
                i.putExtra("user",Parcels.wrap((User)ivProfileImage.getTag(R.string.user)));
                context.startActivity(i);

            }
        });
        //5. Return the view to be inserted into the list
        return convertView;
    }
}
