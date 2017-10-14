package com.example.moetaz.backingapp.models;

import java.io.Serializable;
import java.util.List;

/**
 * Created by moetaz on 10/10/2017.
 */

public class RecipeModel implements Serializable {
    private String name;
    private String image;
    private List<ingredients> ingredientsList;
    private List<steps> stepsList;

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public List<ingredients> getIngredientsList() {
        return ingredientsList;
    }

    public List<steps> getStepsList() {
        return stepsList;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIngredientsList(List<ingredients> ingredientsList) {
        this.ingredientsList = ingredientsList;
    }

    public void setStepsList(List<steps> stepsList) {
        this.stepsList = stepsList;
    }

    public static class ingredients implements Serializable{
        private String quantity;
        private String measure;
        private String ingredient;

        public String getQuantity() {
            return quantity;
        }

        public String getMeasure() {
            return measure;
        }

        public String getIngredient() {
            return ingredient;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setMeasure(String measure) {
            this.measure = measure;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }
    }

    public static class steps implements Serializable{
        private String shortDescription;
        private String description;
        private String videoURL;

        public String getShortDescription() {
            return shortDescription;
        }

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }
    }
}
