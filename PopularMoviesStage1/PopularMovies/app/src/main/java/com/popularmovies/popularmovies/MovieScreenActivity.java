package com.popularmovies.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MovieScreenActivity extends AppCompatActivity
{
    private String mMovieName;
    private String mUserRating;
    private String mReleaseDate;
    private String mPlot;
    private String mPosterUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra("original_title"))
        {
            mMovieName = intentThatStartedThisActivity.getStringExtra("original_title");
            mUserRating = intentThatStartedThisActivity.getStringExtra("user_rating");
            mReleaseDate = intentThatStartedThisActivity.getStringExtra("release_date");
            mPlot = intentThatStartedThisActivity.getStringExtra("plot");
            mPosterUrl = intentThatStartedThisActivity.getStringExtra("poster_url");
        }

        TextView movie_name_tv = (TextView) this.findViewById(R.id.movie_name);
        movie_name_tv.setText(mMovieName);

        TextView user_rating_tv = (TextView) this.findViewById(R.id.user_rating);
        user_rating_tv.setText("User rating: " + mUserRating);

        TextView release_date_tv = (TextView) this.findViewById(R.id.release_date);
        release_date_tv.setText("Release Date: " + mReleaseDate);

        TextView plot_tv = (TextView) this.findViewById(R.id.movie_plot);
        plot_tv.setText(mPlot);

        ImageView poster_iv = (ImageView) this.findViewById(R.id.movie_poster);
        Picasso.with(poster_iv.getContext()).load(mPosterUrl).resize(100, 150).into(poster_iv);
    }
}
