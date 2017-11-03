package com.example.moetaz.bakingapp.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.fragments.RecipeInfoFragment;
import com.example.moetaz.bakingapp.utilities.MyUtilities;

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
