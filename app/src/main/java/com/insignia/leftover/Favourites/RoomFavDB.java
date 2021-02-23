package com.insignia.leftover.Favourites;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.insignia.leftover.Recipe.CustomListAdapter;

@Database(entities = FavData.class,version = 5,exportSchema = false)
public abstract class RoomFavDB extends RoomDatabase {
    private static RoomFavDB database;

    private static String Database_name = "database";

    public synchronized static RoomFavDB getInstance(Context context) {
        if(database == null){
            database = Room.databaseBuilder(context.getApplicationContext(),RoomFavDB.class,Database_name).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract FavDao favDao();
}


