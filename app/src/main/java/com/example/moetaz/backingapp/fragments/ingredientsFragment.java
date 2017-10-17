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

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.adapters.IngredientAdapter;
import com.example.moetaz.backingapp.adapters.RecipeAdapter;
import com.example.moetaz.backingapp.models.RecipeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ingredientsFragment extends Fragment {
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
    IngredientAdapter ingredientAdapter;
    public ingredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        ingredientses = (List<RecipeModel.ingredients>) intent.getSerializableExtra("IngPass");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView  = view.findViewById(R.id.IngredientRecyler);
        recyclerView.hasFixedSize();
        SetGridManager();
        ingredientAdapter = new IngredientAdapter(getContext(),ingredientses);
        recyclerView.setAdapter(ingredientAdapter);

        return view;
    }
    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 1);

        /*
        if(MainActivity.IsTowPane)
            gridLayoutManager = new GridLayoutManager(getActivity(), 3);
            */

        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
