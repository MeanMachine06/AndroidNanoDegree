package com.bt.bakingtime.activities.recipedetaillist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bt.bakingtime.R;
import com.bt.bakingtime.data.Recipe;

import java.util.ArrayList;

/**
 * Created by Aditya on 8/21/2017.
 */

public class RecipeDetailListFragment extends Fragment
{
    private LinearLayout mIngredientsLayout;
    private LinearLayout mRecipeStepsLayout;

    public RecipeDetailListFragment()
    {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_recipe_detail_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mIngredientsLayout = (LinearLayout) view.findViewById(R.id.ll_ingredients_layout);
        mRecipeStepsLayout = (LinearLayout) view.findViewById(R.id.ll_recipe_steps_layout);
    }

    public void setRecipe(Recipe recipe)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());

        ArrayList<Recipe.Ingredients> ingredients = recipe.getIngrediants();
        int numOfIngredients = ingredients.size();
        for (int i = 0; i < numOfIngredients; i++)
        {
            Recipe.Ingredients ingredient = ingredients.get(i);
            View view = layoutInflater.inflate(R.layout.item_layout_ingredients_list, mIngredientsLayout, false);

            String ingredientName = ingredient.getIngredient();
            ingredientName = ingredientName.substring(0, 1).toUpperCase() + ingredientName.substring(1);
            TextView ingredientNameTextView = (TextView) view.findViewById(R.id.tv_ingredient_name);
            ingredientNameTextView.setText(ingredientName);

            TextView ingredientQuantity = (TextView) view.findViewById(R.id.tv_ingredient_quantity);
            ingredientQuantity.setText(ingredient.getQuantity() + " " + ingredient.getMeasure());

            mIngredientsLayout.addView(view);
        }

        ArrayList<Recipe.RecipeStep> recipeSteps = recipe.getRecipeSteps();
        int numOfSteps = recipeSteps.size();
        for (int j = 0; j < numOfSteps; j++)
        {
            Recipe.RecipeStep recipeStep = recipeSteps.get(j);
            View view = layoutInflater.inflate(R.layout.item_layout_recipe_steps_list, mRecipeStepsLayout, false);

            TextView recipeStepTextView = (TextView) view.findViewById(R.id.tv_step_name);
            recipeStepTextView.setText(String.valueOf(j + 1) + ". " + recipeStep.getShortDescription());

            mRecipeStepsLayout.addView(view);
        }
    }
}
