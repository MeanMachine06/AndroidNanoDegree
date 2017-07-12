package com.popularmovies.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.popularmovies.popularmovies.utils.MovieData;
import com.squareup.picasso.Picasso;

public class MovieScreenActivity extends AppCompatActivity
{
    private MovieData mMovieData;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_screen);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity != null && intentThatStartedThisActivity.hasExtra("movie_data"))
        {
            mMovieData = intentThatStartedThisActivity.getParcelableExtra("movie_data");

            TextView movie_name_tv = (TextView) this.findViewById(R.id.movie_name);
            movie_name_tv.setText(mMovieData.getOriginalTitle());

            TextView user_rating_tv = (TextView) this.findViewById(R.id.user_rating);
            user_rating_tv.setText(getString(R.string.user_rating, mMovieData.getUserRating()));

            TextView release_date_tv = (TextView) this.findViewById(R.id.release_date);
            release_date_tv.setText(getString(R.string.release_date, mMovieData.getReleaseDate()));

            TextView plot_tv = (TextView) this.findViewById(R.id.movie_plot);
            plot_tv.setText(mMovieData.getPlot());

            ImageView poster_iv = (ImageView) this.findViewById(R.id.movie_poster);
            Picasso.with(poster_iv.getContext()).load(mMovieData.getMoviePosterURL()).resize(100, 150).into(poster_iv);
        }
    }
}
