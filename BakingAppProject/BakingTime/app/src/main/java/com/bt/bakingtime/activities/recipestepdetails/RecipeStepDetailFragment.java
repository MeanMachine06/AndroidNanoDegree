package com.bt.bakingtime.activities.recipestepdetails;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.bakingtime.R;
import com.bt.bakingtime.data.Recipe;

public class RecipeStepDetailFragment extends Fragment
{
    private Recipe.RecipeStep mRecipeStep;
    private TextView mUpperTextView;
    private TextView mLowerTextView;

    public RecipeStepDetailFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        mUpperTextView = (TextView) view.findViewById(R.id.tv_step_upper_label);
        mLowerTextView = (TextView) view.findViewById(R.id.tv_step_lower_label);
    }

    public void setRecipeStepDetail(Recipe.RecipeStep recipeStep)
    {
        mRecipeStep = recipeStep;

        mUpperTextView.setText("Step: " + mRecipeStep.getShortDescription());
        mLowerTextView.setText(mRecipeStep.getLongDescription());
    }
}
