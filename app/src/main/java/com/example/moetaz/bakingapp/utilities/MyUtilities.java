package com.example.moetaz.bakingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.example.moetaz.bakingapp.R;

/**
 * Created by Moetaz on 9/21/2017.
 */

public class MyUtilities {

    public static boolean IsIngredientFragment = false;


    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void message(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public static boolean IsTablet(Context context){
        return context.getResources().getBoolean(R.bool.isTablet);

    }
}
