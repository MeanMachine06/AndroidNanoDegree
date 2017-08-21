package com.bt.bakingtime;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bt.bakingtime.data.Recipe;

import java.util.ArrayList;

/**
 * Created by Aditya on 8/18/2017.
 */

public class RecipeListFragment extends Fragment
{
    private RecipeListAdapter mRecipeListAdapter;

    public RecipeListFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        Recipe r1 = new Recipe("1", "My First Recipe", "10", "");
        recipes.add(r1);
        Recipe r2 = new Recipe("2", "My Second Recipe", "4", "");
        recipes.add(r2);

        mRecipeListAdapter = new RecipeListAdapter(this.getContext(), recipes);
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
