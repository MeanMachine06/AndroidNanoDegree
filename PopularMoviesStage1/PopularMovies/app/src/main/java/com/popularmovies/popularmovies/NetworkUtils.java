package com.popularmovies.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Aditya on 2/8/2017.
 */

public class NetworkUtils
{
    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String TMDB_API_KEY = "";
    private static final String STATIC_TMDB_URL = "https://api.themoviedb.org/3/movie";
    private static final String API_KEY_PARAM = "api_key";
    private static final String LANGUAGE_PARAM = "language";
    private static final String PAGE_PARAM = "page";

    public static URL buildUrl(String sortOrder, String pageNumber)
    {
        Uri builtUri = Uri.parse(STATIC_TMDB_URL).buildUpon()
                .appendPath(sortOrder)
                .appendQueryParameter(API_KEY_PARAM, TMDB_API_KEY)
                .appendQueryParameter(LANGUAGE_PARAM, "en-US")
                .appendQueryParameter(PAGE_PARAM, pageNumber)
                .build();

        URL url = null;

        try
        {
            url = new URL(builtUri.toString());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException
    {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try
        {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String[] getMoviePosterURLArray(String movieDetailsStr)
    {
        String[] posterURLArray = null;
        final String OWM_MESSAGE_CODE = "cod";

        try
        {
            JSONObject movieDetailsJson = new JSONObject(movieDetailsStr);

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

            JSONArray movieResultsArray = movieDetailsJson.getJSONArray("results");
            posterURLArray = new String[movieResultsArray.length()];

            for (int i=0; i < movieResultsArray.length(); i++)
            {
                JSONObject movieObject = movieResultsArray.getJSONObject(i);
                posterURLArray[i] = " http://image.tmdb.org/t/p/w185" + movieObject.getString("poster_path");
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return posterURLArray;
    }
}
