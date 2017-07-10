package com.popularmovies.popularmovies.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Aditya on 7/8/2017.
 */

public class JSONUtils
{
    public static MovieData[] getMovieData(String movieDetailsStr)
    {
        MovieData[] moviesData = null;
        JSONArray movieResultsArray = null;

        try
        {
            movieResultsArray = getMovieResultsArray(movieDetailsStr);
            moviesData = new MovieData[movieResultsArray.length()];

            for (int i=0; i < movieResultsArray.length(); i++)
            {
                JSONObject movieObject = movieResultsArray.getJSONObject(i);
                String id = movieObject.getString("id");
                String originalTitle = movieObject.getString("original_title");
                String posterURL = "http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
                String plot = movieObject.getString("overview");
                String userRating = movieObject.getString("vote_average");
                String releaseData = movieObject.getString("release_date");
                MovieData md = new MovieData(id, originalTitle, posterURL, plot, userRating, releaseData);
                moviesData[i] = md;
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return moviesData;
    }

    /*
    public static String[] getMovieIDsArray(String movieDetailsStr)
    {
        String[] movieIDsArray = null;
        JSONArray movieResultsArray = null;

        try
        {
            movieResultsArray = getMovieResultsArray(movieDetailsStr);
            movieIDsArray = new String[movieResultsArray.length()];

            for (int i=0; i < movieResultsArray.length(); i++)
            {
                JSONObject movieObject = movieResultsArray.getJSONObject(i);
                movieIDsArray[i] = movieObject.getString("id");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return movieIDsArray;
    }

    public static String[] getMoviePosterURLArray(String movieDetailsStr)
    {
        String[] posterURLArray = null;
        JSONArray movieResultsArray = null;

        try
        {
            movieResultsArray = getMovieResultsArray(movieDetailsStr);
            posterURLArray = new String[movieResultsArray.length()];

            for (int i=0; i < movieResultsArray.length(); i++)
            {
                JSONObject movieObject = movieResultsArray.getJSONObject(i);
                posterURLArray[i] = "http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return posterURLArray;
    }
    */

    private static JSONArray getMovieResultsArray(String movieDetailsString)
    {
        final String OWM_MESSAGE_CODE = "cod";
        JSONObject movieDetailsJson = null;
        JSONArray movieResultsArray = null;

        try
        {
            movieDetailsJson = new JSONObject(movieDetailsString);

            /* Is there an error? */
            if (movieDetailsJson.has(OWM_MESSAGE_CODE))
            {
                int errorCode = movieDetailsJson.getInt(OWM_MESSAGE_CODE);

                switch (errorCode)
                {
                    case HttpURLConnection.HTTP_OK:
                        break;
                    case HttpURLConnection.HTTP_NOT_FOUND:
                        return null;
                    default:
                        /* Server probably down */
                        return null;
                }
            }

            movieResultsArray = movieDetailsJson.getJSONArray("results");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return movieResultsArray;
    }
}
