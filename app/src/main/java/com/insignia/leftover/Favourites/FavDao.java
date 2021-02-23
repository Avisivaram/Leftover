package com.insignia.leftover.Favourites;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FavDao {
    @Insert(onConflict = REPLACE)
    void insert(FavData favData);

    @Query("DELETE FROM Favourites WHERE title LIKE :stitle")
    void delTitle(String stitle);

    @Delete
    void reset(List<FavData> favData);

    @Query("SELECT * FROM Favourites")
    List<FavData> getAll();
}
