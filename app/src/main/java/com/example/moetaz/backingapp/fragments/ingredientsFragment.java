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
import com.example.moetaz.backingapp.datastorage.DBAdadpter;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.utilities.MyUtilities;

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
    DBAdadpter dbAdadpter;
    public ingredientsFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getActivity().getIntent();
        ingredientses = (List<RecipeModel.ingredients>) intent.getSerializableExtra("IngPass");
        if(ingredientses.size() > 0){
            dbAdadpter = DBAdadpter.getDBAdadpterInstance(getContext());
            dbAdadpter.DeleteAllDate();
             for(int i = 0;i<ingredientses.size();i++){
                 Long l=dbAdadpter.Insert(ingredientses.get(i).getQuantity(),ingredientses.get(i).getMeasure(),
                         ingredientses.get(i).getIngredient());
                 MyUtilities.message(getContext(),l+"");
             }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        recyclerView.setLayoutManager(gridLayoutManager);
    }

}
