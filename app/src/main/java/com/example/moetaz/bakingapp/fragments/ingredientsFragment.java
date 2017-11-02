package com.example.moetaz.bakingapp.fragments;


import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.adapters.IngredientAdapter;
import com.example.moetaz.bakingapp.datastorage.SharedPref;
import com.example.moetaz.bakingapp.models.RecipeModel;
import com.example.moetaz.bakingapp.utilities.MyUtilities;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.moetaz.bakingapp.utilities.IngProivderConstants.CONTENT_URI_1;
import static com.example.moetaz.bakingapp.utilities.IngProivderConstants.INGGREDIENT;
import static com.example.moetaz.bakingapp.utilities.IngProivderConstants.MEASURE;
import static com.example.moetaz.bakingapp.utilities.IngProivderConstants.QUANTITY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ingredientsFragment extends Fragment {
    FloatingActionButton fab;
    GridLayoutManager gridLayoutManager;
    @BindView(R.id.IngredientRecyler)
    RecyclerView recyclerView;
    private List<RecipeModel.ingredients> ingredientses = new ArrayList<>();
    IngredientAdapter ingredientAdapter;
    String Name;
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
        Name = intent.getStringExtra("rName");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_ingredients, container, false);
        ButterKnife.bind(this, view);
        fab = view.findViewById(R.id.fab);

        recyclerView.hasFixedSize();
        SetGridManager();
        ingredientAdapter = new IngredientAdapter(getContext(), ingredientses);
        recyclerView.setAdapter(ingredientAdapter);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SharedPref(getContext()).SaveItem("RecipeName", Name);
                contentResolver.delete(CONTENT_URI_1,
                        null, null);
                for (int i = 0; i < ingredientses.size(); i++) {
                    ContentValues cv = new ContentValues();
                    cv.put(QUANTITY, ingredientses.get(i).getQuantity());
                    cv.put(MEASURE, ingredientses.get(i).getMeasure());
                    cv.put(INGGREDIENT, ingredientses.get(i).getIngredient());

                    contentResolver.insert(CONTENT_URI_1, cv);

                }
                Toast.makeText(getContext(), "ingredients added to the widget ", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void SetGridManager() {
        gridLayoutManager = new GridLayoutManager(getActivity(), 1);

        recyclerView.setLayoutManager(gridLayoutManager);
    }


}
