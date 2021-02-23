package com.insignia.leftover.Favourites;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Favourites")
public class FavData implements Serializable {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "imageURL")
    private String imageURL;

    @ColumnInfo(name = "recipeURL")
    private String recipeURL;

    @ColumnInfo(name = "duration")
    private int duration;

    @ColumnInfo(name = "difficulty")
    private int difficulty;

    public String getTitle() {
        return title;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getRecipeURL() {
        return recipeURL;
    }

    public void setRecipeURL(String recipeURL) {
        this.recipeURL = recipeURL;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public FavData(@NonNull String title, String imageURL, String recipeURL, int duration, int difficulty) {
        this.title = title;
        this.imageURL = imageURL;
        this.recipeURL = recipeURL;
        this.duration = duration;
        this.difficulty = difficulty;
    }
}
