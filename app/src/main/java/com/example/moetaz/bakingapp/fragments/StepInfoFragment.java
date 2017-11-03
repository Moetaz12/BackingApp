package com.example.moetaz.bakingapp.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.activities.MainActivity;
import com.example.moetaz.bakingapp.datastorage.SharedPref;
import com.example.moetaz.bakingapp.models.RecipeModel;
import com.example.moetaz.bakingapp.utilities.Constants;
import com.example.moetaz.bakingapp.utilities.MyUtilities;
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
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
//TextUtils.isEmpty
public class StepInfoFragment extends Fragment {
    @BindView(R.id.app_bar) Toolbar toolbar;
    @BindView(R.id.video_img)
    ImageView img;
    @BindView(R.id.back)
    ImageView Back;
    @BindView(R.id.forward)
    ImageView Forward;
    @BindView(R.id.relativeStep)
    View innerRoot;
    @BindView(R.id.stepdesc)
    TextView textView;
    @BindView(R.id.exoView)
    SimpleExoPlayerView simpleExoPlayerView;
    private List<RecipeModel.steps> stepses = new ArrayList<>();
    private SimpleExoPlayer simpleExoPlayer;
    private RecipeModel.steps step;
    int position;
    int cPosition;

    public StepInfoFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         getActivity().setTitle(new SharedPref(getContext()).GetItem(Constants.Action_Bar_Title_Key));
        if (savedInstanceState != null) {
            stepses = (List<RecipeModel.steps>) savedInstanceState.getSerializable("mlist");
            position = savedInstanceState.getInt("p");
        } else {
            Intent intent = getActivity().getIntent();
            if (!MyUtilities.IsTablet(getContext())) {
                stepses = (List<RecipeModel.steps>) intent.getSerializableExtra(Constants.Step_Pass_Key);
                position = intent.getIntExtra("position", 0);
            } else {
                stepses = (List<RecipeModel.steps>) getArguments().getSerializable(Constants.Step_Pass_Key);
                position = getArguments().getInt("position");
            }
        }

        step = stepses.get(position);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_step_info, container, false);
         ButterKnife.bind(this, view);
         ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        cPosition = 0;
        if(savedInstanceState != null){
            cPosition=savedInstanceState.getInt("CurrentPosition");
            MyUtilities.message(getContext(),cPosition+"");
        }
        if (!step.getVideoURL().equals("")) {
            DisplayVideo(Uri.parse(step.getVideoURL()),cPosition);
        } else if (!step.getThumbnailURL().equals("")  ) {
            img.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setVisibility(View.GONE);
            Picasso.with(getContext()).load(step.getThumbnailURL()).into(img);

        } else {
            img.setVisibility(View.VISIBLE);
            simpleExoPlayerView.setVisibility(View.GONE);
            img.setBackgroundResource(R.drawable.novid);
        }


        textView.setText(step.getDescription());

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position > 0) {
                    GoToPreviousStep();
                }

            }
        });
        Forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < stepses.size() - 1) {
                    GoToNextStep();
                }
            }
        });

        if (IsLandscape()) {
            HideActionBar();
            innerRoot.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
        if (MyUtilities.IsTablet(getContext())) {
            Back.setVisibility(View.GONE);
            Forward.setVisibility(View.GONE);
        }

        return view;
    }

    private void GoToNextStep() {

        RefreshFragment(++position);
    }

    private void RefreshFragment(int i) {
        Fragment frg;
        frg = getActivity().getSupportFragmentManager().findFragmentByTag("stepinfo");
        final FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(frg);
        ft.attach(frg);
        step = stepses.get(i);
        ft.commit();
    }

    private void GoToPreviousStep() {

        RefreshFragment(--position);
    }


    private void HideActionBar() {
        try {
            ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private boolean IsLandscape() {
        int ot = getResources().getConfiguration().orientation;
        return (Configuration.ORIENTATION_LANDSCAPE == ot);
    }

    private void DisplayVideo(Uri uri,int index) {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);


            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exop");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();

            MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory, extractorsFactory, null, null);
            simpleExoPlayerView.setPlayer(simpleExoPlayer);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.seekTo((long)index);
            simpleExoPlayer.setPlayWhenReady(true);

        } catch (Exception e) {
            MyUtilities.message(getContext(), e.getMessage());
            ;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mlist", (Serializable) stepses);
        outState.putInt("p", position);
        outState.putInt("CurrentPosition", (int) simpleExoPlayer.getCurrentPosition());
        MyUtilities.message(getContext(),(int) simpleExoPlayer.getCurrentPosition()+"");


    }

    @Override
    public void onPause() {
        super.onPause();
         if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }



    @Override
    public void onDetach() {
         super.onDetach();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
         super.onDestroyView();
        if (simpleExoPlayer != null) {
            simpleExoPlayer.stop();
            simpleExoPlayer.release();
            simpleExoPlayer = null;
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        try {

            ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        } catch (Exception e) {
            MyUtilities.message(getContext(),"error");
        }
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
             case android.R.id.home:
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));

                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}

