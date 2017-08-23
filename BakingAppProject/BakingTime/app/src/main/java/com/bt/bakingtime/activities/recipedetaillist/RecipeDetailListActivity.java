package com.bt.bakingtime.activities.recipedetaillist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.bt.bakingtime.R;
import com.bt.bakingtime.data.Recipe;

/**
 * Created by Aditya on 8/22/2017.
 */

public class RecipeDetailListActivity extends AppCompatActivity
{
    private Recipe mRecipe;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_recipe_detail_list);

        mRecipe = getIntent().getParcelableExtra("recipe_data");
        Toast.makeText(this, "Recipe: " + mRecipe.getRecipeName(), Toast.LENGTH_SHORT).show();

        TextView mRecipeNameTextView = (TextView) this.findViewById(R.id.tv_recipe_name);
        mRecipeNameTextView.setText(mRecipe.getRecipeName());

        RecipeDetailListFragment recipeDetailListFragment = (RecipeDetailListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_recipe_detail_list);
        recipeDetailListFragment.setRecipe(mRecipe);
    }
}
