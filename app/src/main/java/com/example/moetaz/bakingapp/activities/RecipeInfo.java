package com.example.moetaz.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.fragments.RecipeInfoFragment;

public class RecipeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frecioeinfo,new RecipeInfoFragment()).commit();
        }
    }
}
