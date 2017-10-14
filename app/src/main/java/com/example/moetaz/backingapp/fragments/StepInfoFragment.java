package com.example.moetaz.backingapp.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.utilities.MyUtilities;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import static android.R.id.message;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepInfoFragment extends Fragment {

    SimpleExoPlayer simpleExoPlayer;
    SimpleExoPlayerView simpleExoPlayerView;
    RecipeModel.steps step;
    TextView textView;
    public StepInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Do();
        Intent intent = getActivity().getIntent();
        step = (RecipeModel.steps) intent.getSerializableExtra("stepPass");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_info, container, false);
        simpleExoPlayerView = view.findViewById(R.id.exoView);
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);

            MyUtilities.message(getActivity(),step.getVideoURL());
            Uri VideoUri = Uri.parse(step.getVideoURL());
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exop");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(VideoUri,dataSourceFactory ,extractorsFactory,null,null);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(false);


        } catch (Exception e) {
            MyUtilities.message(getContext(),e.getMessage()); ;
        }

        textView = view.findViewById(R.id.stepdesc);
        textView.setText(step.getDescription());

        if(IsLandscape())
            textView.setVisibility(View.GONE);
        return view;
    }

    private void Do(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    private boolean IsLandscape(){
        int ot = getResources().getConfiguration().orientation;
        return (Configuration.ORIENTATION_LANDSCAPE == ot);
    }
}
