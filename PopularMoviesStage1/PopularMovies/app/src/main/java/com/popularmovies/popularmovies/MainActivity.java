package com.popularmovies.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.popularmovies.popularmovies.utils.JSONUtils;
import com.popularmovies.popularmovies.utils.MovieData;
import com.popularmovies.popularmovies.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MyAdapter.ListItemClickListener
{
    private static String TAG = MainActivity.class.getSimpleName();

    private final String SORT_BY_POPULAR = "popular";
    private final String SORT_BY_TOP_RATED = "top_rated";

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate..");

        setContentView(R.layout.activity_main);

        Picasso.with(this).setLoggingEnabled(true);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_grid_view);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        myAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(myAdapter);

        new FetchMoviesData().execute(SORT_BY_POPULAR);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemIdSelected = item.getItemId();
        Context context = MainActivity.this;
        String message = "";

        switch (itemIdSelected)
        {
            case R.id.action_sort_by_most_populer:
                message = "Sort by most popular";
                new FetchMoviesData().execute(SORT_BY_POPULAR);
                break;
            case R.id.action_sort_by_top_rated:
                message = "Sort by top rated";
                new FetchMoviesData().execute(SORT_BY_TOP_RATED);
                break;
        }

        if(!message.equals(""))
        {
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClickListener(MovieData clickedMovieData)
    {
        //Toast.makeText(MainActivity.this, clickedMovieData.getOriginalTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, MovieScreenActivity.class);
        intent.putExtra("movie_data", clickedMovieData);
        startActivity(intent);
    }

    class FetchMoviesData extends AsyncTask<String, Void, MovieData[]>
    {

        @Override
        protected MovieData[] doInBackground(String... params)
        {
            MovieData[] moviesData = null;
            String sortByText = params[0];

            URL movieListUrl = NetworkUtils.buildUrl(sortByText, "1");

            try
            {
                String jsonResponse = NetworkUtils.getResponseFromHttpUrl(movieListUrl);
                moviesData = JSONUtils.getMovieData(jsonResponse);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return moviesData;
        }

        @Override
        protected void onPostExecute(MovieData[] moviesData)
        {
            Log.d(TAG, "onPostExecute");
            myAdapter.setMoviesData(moviesData);
        }
    }
}
