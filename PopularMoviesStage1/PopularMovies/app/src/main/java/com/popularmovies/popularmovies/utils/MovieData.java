package com.popularmovies.popularmovies.utils;

/**
 * Created by Aditya on 7/8/2017.
 */

public class MovieData
{
    private String mMovieId;
    private String mOriginalTitle;
    private String mMoviePosterURL;
    private String mPlot;
    private String mUserRating;
    private String mReleaseDate;

    public MovieData(String movieID, String originalTitle, String posterURL, String plot, String userRating, String releaseData)
    {
        mMovieId = movieID;
        mOriginalTitle = originalTitle;
        mMoviePosterURL = posterURL;
        mPlot = plot;
        mUserRating = userRating;
        mReleaseDate = releaseData;
    }

    public String getMovieId()
    {
        return mMovieId;
    }

    public String getOriginalTitle()
    {
        return mOriginalTitle;
    }

    public String getMoviePosterURL()
    {
        return mMoviePosterURL;
    }

    public String getPlot()
    {
        return mPlot;
    }

    public String getUserRating()
    {
        return mUserRating;
    }

    public String getReleaseDate()
    {
        return mReleaseDate;
    }
}
