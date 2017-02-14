package com.popularmovies.popularmovies;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity
{
    private static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate..");

        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.rv_grid_view);
        mGridLayoutManager = new GridLayoutManager(this, 2);

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        myAdapter = new MyAdapter();
        mRecyclerView.setAdapter(myAdapter);

        new FetchMovieData().execute();
    }

    public class FetchMovieData extends AsyncTask<String, Void, String[]>
    {

        @Override protected String[] doInBackground(String... params)
        {
            String[] posterUrls = null;
            URL movieListUrl = NetworkUtils.buildUrl("popular", "1");
            try
            {
                String jsonWeatherResponse = NetworkUtils.getResponseFromHttpUrl(movieListUrl);
                posterUrls = NetworkUtils.getMoviePosterURLArray(jsonWeatherResponse);

                for (int i=0; i < posterUrls.length; i++)
                {
                    Log.d(TAG, posterUrls[i]);
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return posterUrls;
        }

        @Override
        protected void onPostExecute(String[] posterUrls)
        {
            myAdapter.setMoviePostersData(posterUrls);
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>
    {
        private String[] mDataSet;

        public MyAdapter()
        {
            mDataSet = new String[10];
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_image_view_layout, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position)
        {
            holder.setImage(mDataSet[position]);
        }

        @Override
        public int getItemCount()
        {
            if(mDataSet == null)
            {
                return 0;
            }

            return mDataSet.length;
        }

        public void setMoviePostersData(String[] posterUrlData)
        {
            mDataSet = posterUrlData;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            public ImageView mImageView;

            public ViewHolder(View view)
            {
                super(view);
                mImageView = (ImageView) view.findViewById(R.id.img_android);
            }

            public void setImage(String imageUrl)
            {
                Log.d(TAG, "Setting picasso:" + imageUrl);
                Picasso.with(mImageView.getContext()).load(imageUrl).resize(40, 40).into(mImageView);
            }
        }
    }
}
