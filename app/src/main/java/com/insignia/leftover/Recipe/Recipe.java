package com.insignia.leftover.Recipe;

import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.IOException;
public class Recipe {


    private String title;
    private String imageUrl;
    private String url;
    private int duration; // Cooking time in minutes
    private double difficulty;

    public double getDifficulty() {
        return difficulty;
    }

    public Recipe(JsonObject recipe_json) {
        JsonObject recipe_elem = (JsonObject) recipe_json.get("recipe");
        this.title = recipe_elem.get("label").getAsString();
        this.url = recipe_elem.get("url").getAsString();
        this.duration = recipe_elem.get("totalTime").getAsInt();
        this.imageUrl = recipe_elem.get("image").getAsString();
        this.difficulty = recipe_elem.getAsJsonArray("ingredients").size();
    }

    public String getTitle() { return title; }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getDuration() {
        return duration;
    }

    public String getUrl() {
        return url;
    }

    public static void main(String[] args) throws IOException, JSONException {
        // this function is here only to test the validity and the format of the returned info
//        String[] ingredients = {"cheese", "chicken"};
//        String url = ChooseIngredients.buildUrl(ingredients);
//        RecipesFactory.getRecipes(url);
    }

    public Recipe(String title, String imageUrl, String url, int duration, double difficulty) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.url = url;
        this.duration = duration;
        this.difficulty = difficulty;
    }
}
