package com.bt.bakingtime;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.bakingtime.data.Recipe;
import com.bt.bakingtime.utils.JSONUtils;
import com.bt.bakingtime.utils.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Aditya on 8/18/2017.
 */

public class RecipeListFragment extends Fragment
{
    private ArrayList<Recipe> mRecipiesData;
    private RecipeListAdapter mRecipeListAdapter;

    public RecipeListFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        getRecipiesData();
        mRecipeListAdapter = new RecipeListAdapter(this.getContext(), mRecipiesData);
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

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recipeListRecyclerView = (RecyclerView) view.findViewById(R.id.rv_recipe_list);
        recipeListRecyclerView.setAdapter(mRecipeListAdapter);
        recipeListRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}
