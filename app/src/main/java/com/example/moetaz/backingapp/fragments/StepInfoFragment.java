package com.example.moetaz.backingapp.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepInfoFragment extends Fragment {
    @BindView(R.id.back )   ImageView Back;
    @BindView(R.id.forward )   ImageView Forward;
    private List<RecipeModel.steps> stepses = new ArrayList<>();

    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView simpleExoPlayerView;
    private RecipeModel.steps step;
    TextView textView;
    View innerRoot;
    int position;
    Uri VideoUri;
    public StepInfoFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HideActionBar();

        Intent intent = getActivity().getIntent();
        if (!MyUtilities.IsTowPane) { //mobile
            stepses = (List<RecipeModel.steps>) intent.getSerializableExtra("stepPass");
            position = intent.getIntExtra("position",0);
        }else { //tablet
            stepses = (List<RecipeModel.steps>) getArguments().getSerializable("stepPass");
            position = getArguments().getInt("position");
        }
            step = stepses.get(position);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_step_info, container, false);
            ButterKnife.bind(this, view);
        innerRoot = view.findViewById(R.id.relativeStep);
        simpleExoPlayerView = view.findViewById(R.id.exoView);
        DisplayVideo();
        textView = view.findViewById(R.id.stepdesc);
        textView.setText(step.getDescription());
        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position > 0){
                    GoToPreviousStep(position);
                }

            }
        });
        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position < stepses.size()-1 ){
                    GoToNextStep(position);
                }
            }
        });

        if(IsLandscape()) {
            innerRoot.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }

        return view;
    }

    private void GoToNextStep(int position) {
        MyUtilities.message(getContext(),"Forward");
        Fragment frg  ;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("stepinfo");
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        step = stepses.get(++position);
        ft.commit();
    }

    private void GoToPreviousStep(int position) {
        MyUtilities.message(getContext(),"Back");
        Fragment frg  ;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("stepinfo");
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);

        step = stepses.get(--position);
        ft.commit();
    }


    private void HideActionBar(){
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    private boolean IsLandscape(){
        int ot = getResources().getConfiguration().orientation;
        return (Configuration.ORIENTATION_LANDSCAPE == ot);
    }
    private void DisplayVideo(){
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(),trackSelector);


            VideoUri = Uri.parse(step.getVideoURL());
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exop");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(VideoUri,dataSourceFactory ,extractorsFactory,null,null);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(false);


        } catch (Exception e) {
            MyUtilities.message(getContext(),e.getMessage()); ;
        }
    }
}
