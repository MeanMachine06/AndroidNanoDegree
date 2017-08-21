package com.bt.bakingtime;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bt.bakingtime.data.Recipe;

import java.util.ArrayList;

/**
 * Created by Aditya on 8/17/2017.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder>
{
    private ArrayList<Recipe> mRecipies;
    private Context mContext;

    public RecipeListAdapter(Context context, ArrayList<Recipe> recipes)
    {
        mContext = context;
        mRecipies = recipes;
    }

    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View recipeCardView = layoutInflater.inflate(R.layout.front_recipe_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(recipeCardView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecipeListAdapter.ViewHolder holder, int position)
    {
        Recipe recipe = mRecipies.get(position);
        holder.setRecipe(recipe);
    }

    @Override
    public int getItemCount()
    {
        return mRecipies.size();
    }

    public void setRecipesData(ArrayList<Recipe> recipies)
    {
        mRecipies.clear();
        mRecipies.addAll(recipies);

        this.notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private Recipe mRecipe;

        public ViewHolder(View itemView)
        {
            super(itemView);
        }

        public Recipe getRecipe()
        {
            return mRecipe;
        }

        public void setRecipe(Recipe recipe)
        {
            this.mRecipe = recipe;

            ImageView recipeImageView = (ImageView) itemView.findViewById(R.id.recipe_image);
            TextView recipeNameTextView = (TextView) itemView.findViewById(R.id.recipe_name);
            TextView recipeServingsTextView = (TextView) itemView.findViewById(R.id.recipe_servings);

            recipeNameTextView.setText(mRecipe.getRecipeName());
            recipeServingsTextView.setText("Servings : " + mRecipe.getServings());
        }
    }
}
