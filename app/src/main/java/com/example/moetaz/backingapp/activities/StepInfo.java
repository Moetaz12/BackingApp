package com.example.moetaz.backingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moetaz.backingapp.R;
import com.example.moetaz.backingapp.fragments.StepInfoFragment;

public class StepInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_info);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fstep,new StepInfoFragment()).commit();
        }
    }
}
