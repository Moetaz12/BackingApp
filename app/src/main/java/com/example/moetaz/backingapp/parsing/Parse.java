package com.example.moetaz.backingapp.parsing;

import com.example.moetaz.backingapp.models.RecipeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Moetaz on 9/20/2017.
 */

public class Parse {
    public static List<RecipeModel> parseRecip(String data) {
        List<RecipeModel> recipeModels = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(data);
            JSONArray jsonArray = object.getJSONArray("results");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject finalobject = jsonArray.getJSONObject(i);


            }

            return recipeModels;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
