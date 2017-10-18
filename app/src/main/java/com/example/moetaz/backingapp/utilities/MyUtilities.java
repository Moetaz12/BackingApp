package com.example.moetaz.backingapp.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

/**
 * Created by Moetaz on 9/21/2017.
 */

public class MyUtilities {
    public static boolean IsTowPane = false;
    public static boolean IsIngredientFragment = false;
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    public static void message(Context context,String s){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
}
