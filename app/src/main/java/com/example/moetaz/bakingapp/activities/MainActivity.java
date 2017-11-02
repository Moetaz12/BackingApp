package com.example.moetaz.bakingapp.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.moetaz.bakingapp.R;
import com.example.moetaz.bakingapp.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fmain,new MainFragment()).commit();
        }
    }
}
