package com.example.moetaz.bakingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.activities.MainActivity;
import com.example.moetaz.bakingapp.adapters.RecipeAdapter;
import com.example.moetaz.bakingapp.datastorage.SharedPref;
import com.example.moetaz.bakingapp.models.RecipeModel;
import com.example.moetaz.bakingapp.utilities.Constants;
import com.example.moetaz.bakingapp.utilities.MyUtilities;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInfoFragment extends Fragment {
     GridLayoutManager gridLayoutManager;
     RecipeModel recipeModel ;
    @BindView(R.id.app_bar) Toolbar toolbar;
    @BindView(R.id.recyleviewrecipeinfo )  RecyclerView recyclerView;

    public RecipeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

         getActivity().setTitle(new SharedPref(getContext()).GetItem(Constants.Action_Bar_Title_Key));
        if (savedInstanceState != null) {
            recipeModel = (RecipeModel) savedInstanceState.getSerializable("model");
        }else {
            Intent intent = getActivity().getIntent();
            recipeModel = (RecipeModel) intent.getSerializableExtra(Constants.Model_Key);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        ButterKnife.bind(this, view);
         ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        setHasOptionsMenu(true);

        LoadRecipInfo();
        return view;
    }

    private void LoadRecipInfo() {
        SetGridManager();
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(),recipeModel.getStepsList()
                ,recipeModel.getIngredientsList(),recipeModel.getName(),true);
        recyclerView.setAdapter(recipeAdapter);
    }

    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("model",  recipeModel);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                getActivity().finish();
                startActivity(new Intent(getContext(), MainActivity.class));

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
