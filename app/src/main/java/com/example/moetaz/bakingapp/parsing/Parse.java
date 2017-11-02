package com.example.moetaz.bakingapp.parsing;

import com.example.moetaz.bakingapp.models.RecipeModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Moetaz on 9/20/2017.
 */

public class Parse {
    public static ArrayList<RecipeModel> parseRecip(String data) {
        ArrayList<RecipeModel> recipeModels = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(data);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject finalobject = jsonArray.getJSONObject(i);
                RecipeModel recipeModel = new RecipeModel();
                recipeModel.setName(finalobject.getString("name"));
                recipeModel.setImage(finalobject.getString("image"));

                ArrayList<RecipeModel.ingredients> ingredientses = new ArrayList<>();
                for(int j= 0;j<finalobject.getJSONArray("ingredients").length() ;j++){
                    JSONObject innerObject = finalobject.getJSONArray("ingredients").getJSONObject(j);
                    RecipeModel.ingredients ingredients = new RecipeModel.ingredients();

                    ingredients.setQuantity(innerObject.getString("quantity"));
                    ingredients.setIngredient(innerObject.getString("ingredient"));
                    ingredients.setMeasure(innerObject.getString("measure"));
                    ingredientses.add(ingredients);
                }
                ArrayList<RecipeModel.steps> stepses = new ArrayList<>();
                for(int j= 0;j<finalobject.getJSONArray("steps").length() ;j++){
                    JSONObject innerObject = finalobject.getJSONArray("steps").getJSONObject(j);
                    RecipeModel.steps steps = new RecipeModel.steps();

                    steps.setShortDescription(innerObject.getString("shortDescription"));
                    steps.setDescription(innerObject.getString("description"));
                    steps.setVideoURL(innerObject.getString("videoURL"));
                    steps.setThumbnailURL(innerObject.getString("thumbnailURL"));
                    stepses.add(steps);
                }

                  recipeModel.setStepsList(stepses);
                  recipeModel.setIngredientsList(ingredientses);

                recipeModels.add(recipeModel);

            }

            return recipeModels;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }


}
