package com.example.moetaz.bakingapp.datastorage;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class SharedPref {
    private Context context;
    private SharedPreferences preferences;

    public SharedPref(Context context) {
        this.context = context;
        preferences = this.context.getSharedPreferences(
                "Pref", Context.MODE_APPEND
        );    }

    public void SaveItem(String key,String value){

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key,value);
        editor.apply();
    }
    public void RemoveItem(String Item){
        SharedPreferences.Editor editor = this.preferences.edit();
        editor.remove(Item);
        editor.apply();
    }
    public String GetItem(String key){

        return  preferences.getString(key, "");
    }
}

