package com.example.moetaz.backingapp.fragments;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.adapters.IngredientAdapter;
import com.example.moetaz.backingapp.models.RecipeModel;
import com.example.moetaz.backingapp.utilities.MyUtilities;

import java.util.ArrayList;
import java.util.List;

import static com.example.moetaz.backingapp.utilities.IngProivderConstants.CONTENT_URI_1;
import static com.example.moetaz.backingapp.utilities.IngProivderConstants.INGGREDIENT;
import static com.example.moetaz.backingapp.utilities.IngProivderConstants.MEASURE;
import static com.example.moetaz.backingapp.utilities.IngProivderConstants.QUANTITY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ingredientsFragment extends Fragment {
    Button Add,Delete;
    GridLayoutManager gridLayoutManager;
    RecyclerView recyclerView;
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
    IngredientAdapter ingredientAdapter;

    private ContentResolver contentResolver;
    public ingredientsFragment() {
        // Required empty public constructor
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contentResolver = getActivity().getContentResolver();
        Intent intent = getActivity().getIntent();
        ingredientses = (List<RecipeModel.ingredients>) intent.getSerializableExtra("IngPass");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        recyclerView  = view.findViewById(R.id.IngredientRecyler);
        Add = view.findViewById(R.id.add);
        Delete = view.findViewById(R.id.delete);

        recyclerView.hasFixedSize();
        SetGridManager();
        ingredientAdapter = new IngredientAdapter(getContext(),ingredientses);
        recyclerView.setAdapter(ingredientAdapter);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contentResolver.delete(CONTENT_URI_1,
                        null,null);
                for(int i = 0;i<ingredientses.size();i++){
                    ContentValues cv = new ContentValues();
                    cv.put(QUANTITY,ingredientses.get(i).getQuantity());
                    cv.put(MEASURE,ingredientses.get(i).getMeasure());
                    cv.put(INGGREDIENT,ingredientses.get(i).getIngredient());

                       contentResolver.insert(CONTENT_URI_1,cv);

                }
            }
        });
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               int i= contentResolver.delete(CONTENT_URI_1,
                        null,null);
                MyUtilities.message(getContext(),i+"");
            }
        });
        return view;
    }
    private void SetGridManager(){
        gridLayoutManager=new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
    }


}
