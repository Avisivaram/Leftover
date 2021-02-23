package com.insignia.leftover.Recipe;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/// List of ingredients and amounts.
public class IngredientsList implements Serializable {

    private HashMap<String, Integer> ingredientsMap = new HashMap<>();

    public void addIngredient(String ingredientName) {
        addIngredientWithAmount(ingredientName, 1);
    }

    public void addIngredientWithAmount(String ingredientName, int amount){
        if (ingredientsMap.containsKey(ingredientName)) {
            ingredientsMap.put(ingredientName, ingredientsMap.get(ingredientName) + amount);
        } else {
            ingredientsMap.put(ingredientName, amount);
        }
    }

    public void removeIngredient(String ingredientName) {
        ingredientsMap.remove(ingredientName);
    }

    public void removeIngredientAmount(String ingredientName, int amount){
        if (ingredientsMap.containsKey(ingredientName)) {
            ingredientsMap.put(ingredientName, ingredientsMap.get(ingredientName) - amount);
        } else {
            ingredientsMap.put(ingredientName, amount);
        }
    }


    public void removeAllIngredients() {
        ingredientsMap.clear();
    }


    public ArrayList<String> getIngredientsList() {
        return new ArrayList<>(ingredientsMap.keySet());
    }


}
