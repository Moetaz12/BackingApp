package com.example.moetaz.bakingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.adapters.RecipeAdapter;
import com.example.moetaz.bakingapp.models.RecipeModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInfoFragment extends Fragment {
     GridLayoutManager gridLayoutManager;
     RecipeModel recipeModel ;
    @BindView(R.id.recyleviewrecipeinfo )  RecyclerView recyclerView;

    public RecipeInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        recipeModel = (RecipeModel) intent.getSerializableExtra("modelPass");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);
        ButterKnife.bind(this, view);
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
}
