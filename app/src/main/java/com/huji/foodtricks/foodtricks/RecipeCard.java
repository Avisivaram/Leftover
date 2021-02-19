package com.huji.foodtricks.foodtricks;

public class RecipeCard {
//    private String url;
//    private String title;
//
//    RecipeCard(String url, String title) {
//        this.url = url;
//        this.title = title;
//    }

    private Recipe recipe;

    RecipeCard(Recipe recipe) {
        this.recipe = recipe;
    }

    //    String getRecipeURL() {
//        return url;
//    }
//
//    String getTitle() {
//        return title;
//    }

    String getRecipeURL() {
        return recipe.getUrl();
    }

    String getTitle() {
        return recipe.getTitle();
    }

    String getImageURL() {
        return recipe.getImageUrl();
    }

    int getDuration() {
        return recipe.getDuration();
    }

    double getDifficulty() { return recipe.getDifficulty(); }
}
