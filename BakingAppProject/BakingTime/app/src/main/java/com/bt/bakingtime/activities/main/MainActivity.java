package com.bt.bakingtime.activities.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bt.bakingtime.R;
import com.bt.bakingtime.activities.recipedetaillist.RecipeDetailListActivity;
import com.bt.bakingtime.data.Recipe;
import com.bt.bakingtime.utils.JSONUtils;
import com.bt.bakingtime.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
{
    private ArrayList<Recipe> mRecipiesData;
    private RecipeListAdapter mRecipeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                                     .setDefaultFontPath("fonts/Montserrat-Regular.otf")
                                     .setFontAttrId(R.attr.fontPath)
                                     .build());

        setContentView(R.layout.activity_main);

        getRecipiesData();
        mRecipeListAdapter = new RecipeListAdapter(this, mRecipiesData);
        mRecipeListAdapter.setOnRecipeItemClickListener(new RecipeListAdapter.OnRecipeItemClickListener()
        {
            @Override
            public void onRecipeItemClicked(Recipe recipeClicked)
            {
                Intent intent = new Intent(MainActivity.this, RecipeDetailListActivity.class);
                intent.putExtra("recipe_data", recipeClicked);
                startActivity(intent);
            }
        });

        RecyclerView recipeListRecyclerView = (RecyclerView) this.findViewById(R.id.rv_recipe_list);
        recipeListRecyclerView.setAdapter(mRecipeListAdapter);
        recipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getRecipiesData()
    {
        mRecipiesData = new ArrayList<>();

        new AsyncTask<Void, Void, ArrayList<Recipe>>()
        {
            @Override
            protected ArrayList<Recipe> doInBackground(Void... params)
            {
                try
                {
                    String jsonStringResponse = NetworkUtils.getResponseFromHttpUrl(NetworkUtils.buildUrl());
                    mRecipiesData = JSONUtils.getRecipiesData(jsonStringResponse);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }

                return mRecipiesData;
            }

            @Override
            protected void onPostExecute(ArrayList<Recipe> recipes)
            {
                super.onPostExecute(recipes);
                mRecipeListAdapter.setRecipesData(recipes);
            }
        }.execute();
    }

    @Override protected void attachBaseContext(Context newBase)
    {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
