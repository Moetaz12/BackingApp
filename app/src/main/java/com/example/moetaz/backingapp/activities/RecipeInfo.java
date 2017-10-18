package com.example.moetaz.backingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.fragments.RecipeInfoFragment;
import com.example.moetaz.backingapp.utilities.MyUtilities;

public class RecipeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        if(null != findViewById(R.id.fstep)){
            MyUtilities.IsTowPane = true;
        }

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.frecioeinfo,new RecipeInfoFragment()).commit();
        }
    }
}
