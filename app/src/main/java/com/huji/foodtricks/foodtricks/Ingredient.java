package com.huji.foodtricks.foodtricks;

public class Ingredient {

    private String name;
    private Integer icon;

    public Ingredient(Integer icon, String name) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Integer getIcon() {
        return icon;
    }
}
