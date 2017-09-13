package com.bt.bakingtime.activities.recipestepdetails;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bt.bakingtime.R;
import com.bt.bakingtime.data.Recipe;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;


public class RecipeStepDetailFragment extends Fragment
{
    private Recipe.RecipeStep mRecipeStep;
    private TextView mUpperTextView;
    private TextView mLowerTextView;
    private SimpleExoPlayerView mSimpleExoPlayerView;
    private SimpleExoPlayer mExoPlayer;

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
        mSimpleExoPlayerView = (SimpleExoPlayerView) view.findViewById(R.id.simpleExoPlayerView);
    }

    public void setRecipeStepDetail(Recipe.RecipeStep recipeStep)
    {
        mRecipeStep = recipeStep;

        mUpperTextView.setText("Step: " + mRecipeStep.getShortDescription());
        mLowerTextView.setText(mRecipeStep.getLongDescription());

        initializePlayer(Uri.parse(mRecipeStep.getVideoUrl()));
    }

    private void initializePlayer(Uri mediaUri)
    {
        if(mExoPlayer == null)
        {
            LoadControl loadControl = new DefaultLoadControl();
            TrackSelector trackSelector = new DefaultTrackSelector();

            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            mSimpleExoPlayerView.setPlayer(mExoPlayer);

            String userAgent = Util.getUserAgent(this.getContext(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void releasePlayer()
    {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override public void onDestroy()
    {
        super.onDestroy();
        releasePlayer();
    }
}
