package com.example.moetaz.bakingapp.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref {
    private Context context;
    private SharedPreferences preferences;

    public SharedPref(Context context) {
        this.context = context;
          preferences = PreferenceManager.getDefaultSharedPreferences(this.context);
    }

    public void SaveItem(String key,String value){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }

    public String GetItem(String key){

        return  preferences.getString(key, "");
    }
}

