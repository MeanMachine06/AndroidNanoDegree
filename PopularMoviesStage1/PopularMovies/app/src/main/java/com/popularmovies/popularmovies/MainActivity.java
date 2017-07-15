package com.popularmovies.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
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
    private EndlessRecyclerViewScrollListener mScrollListener;

    private String mCurrentSortOrder = SORT_BY_POPULAR;
    private String mFirstPageNumber = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate..");

        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mCurrentSortOrder = sharedPreferences.getString("sort_order", SORT_BY_POPULAR);

        Picasso.with(this).setLoggingEnabled(false);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_grid_view);
        mGridLayoutManager = new GridLayoutManager(this, calculateNoOfColumns(this));

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        myAdapter = new MyAdapter(this);
        mRecyclerView.setAdapter(myAdapter);

        mScrollListener = new EndlessRecyclerViewScrollListener(mGridLayoutManager)
        {
            @Override public void onLoadMore(int page, int totalItemsCount, RecyclerView view)
            {
                loadMovieList(mCurrentSortOrder, Integer.toString(page));
            }
        };

        mRecyclerView.addOnScrollListener(mScrollListener);
        loadMovieList(mCurrentSortOrder, mFirstPageNumber);
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
                resetMovieList(mCurrentSortOrder);
                loadMovieList(SORT_BY_POPULAR, mFirstPageNumber);
                break;
            case R.id.action_sort_by_top_rated:
                message = "Sort by top rated";
                resetMovieList(mCurrentSortOrder);
                loadMovieList(SORT_BY_TOP_RATED, mFirstPageNumber);
                break;
        }

        if(!message.equals(""))
        {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("sort_order", mCurrentSortOrder);
            editor.apply();

            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void resetMovieList(String sortOrder)
    {
        myAdapter.clearMovieListData();
        mScrollListener.resetState();
    }

    private void loadMovieList(String sortOrder, String pageNum)
    {
        mCurrentSortOrder = sortOrder;
        new FetchMoviesData().execute(sortOrder, pageNum);
    }

    @Override
    public void onListItemClickListener(MovieData clickedMovieData)
    {
        //Toast.makeText(MainActivity.this, clickedMovieData.getOriginalTitle(), Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(MainActivity.this, MovieScreenActivity.class);
        intent.putExtra("movie_data", clickedMovieData);
        startActivity(intent);
    }

    private int calculateNoOfColumns(Context context)
    {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        int scalingFactor = 90;
        int noOfColumns = (int) (dpWidth / scalingFactor);
        return noOfColumns;
    }

    class FetchMoviesData extends AsyncTask<String, Void, MovieData[]>
    {

        @Override
        protected MovieData[] doInBackground(String... params)
        {
            MovieData[] moviesData = null;
            String sortByText = params[0];
            String pageNumber = "1";

            if(params.length > 1)
            {
                pageNumber = params[1];
            }

            URL movieListUrl = NetworkUtils.buildUrl(sortByText, pageNumber);

            try
            {
                Log.d(TAG, "Call to API made for page:" + pageNumber);
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
