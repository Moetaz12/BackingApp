package com.example.moetaz.backingapp.activities;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.fragments.StepInfoFragment;
import com.example.moetaz.backingapp.fragments.ingredientsFragment;
import com.example.moetaz.backingapp.utilities.MyUtilities;

public class StepInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_info);


        if(MyUtilities.IsIngredientFragment){
            MyUtilities.IsIngredientFragment = false;
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fstep,new ingredientsFragment(),"Ingredientinfo").commit();
            }
        }else {
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fstep,new StepInfoFragment(),"stepinfo").commit();
            }
        }

    }
}
