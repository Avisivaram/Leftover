package com.insignia.leftover.Favourites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.RoomDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.insignia.leftover.R;
import com.insignia.leftover.Recipe.CustomListAdapter;
import com.insignia.leftover.Recipe.Recipe;
import com.insignia.leftover.Recipe.RecipeCard;

import java.util.ArrayList;
import java.util.List;

public class FavActivity extends AppCompatActivity {
    ListView favListView;
    ArrayList<RecipeCard> list = new ArrayList<>();


    List<FavData> favDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        RoomFavDB db = RoomFavDB.getInstance(getApplicationContext());
        favListView = findViewById(R.id.favrecipeListView);

        favDataList = db.favDao().getAll();
        for (int i=0;i<favDataList.size();i++){
            list.add(new RecipeCard(new Recipe(favDataList.get(i).getTitle(),favDataList.get(i).getImageURL(),favDataList.get(i).getRecipeURL(),favDataList.get(i).getDuration(),favDataList.get(i).getDifficulty())));
        }
        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.activity_recipes_view, list );
        favListView.setAdapter(adapter);
    }
}

