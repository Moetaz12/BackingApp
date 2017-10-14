package com.example.moetaz.backingapp.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.adapters.RecipeAdapter;
import com.example.moetaz.backingapp.models.RecipeModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeInfoFragment extends Fragment {
     GridLayoutManager gridLayoutManager;
     RecipeModel recipeModel ;
     TextView textView;
     RecyclerView recyclerView;

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
        View view = inflater.inflate(R.layout.fragment_recipe_info, container, false);;
        recyclerView = view.findViewById(R.id.recyleviewrecipeinfo);
        textView = view.findViewById(R.id.ing);
        textView.setText("Recipe ingredients");
        setHasOptionsMenu(true);

        LoadRecipInfo();
        return view;
    }

    private void LoadRecipInfo() {
        SetGridManager();
        RecipeAdapter recipeAdapter = new RecipeAdapter(getContext(),recipeModel.getStepsList(),true);
        recyclerView.setAdapter(recipeAdapter);
    }

    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
    }
}
