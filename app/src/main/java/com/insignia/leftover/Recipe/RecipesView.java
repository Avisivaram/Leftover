package com.insignia.leftover.Recipe;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.insignia.leftover.Home.ChooseIngredients;
import com.insignia.leftover.R;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class RecipesView extends AppCompatActivity {

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.recipes_listview_layout);
        mListView = findViewById(R.id.recipeListView);

        IngredientsList ingrdiensList = (IngredientsList) getIntent().getSerializableExtra(ChooseIngredients.INGREDIENTS);

        ArrayList<RecipeCard> list = new ArrayList<>();

        String url = ChooseIngredients.buildUrl(ingrdiensList.getIngredientsList());
        try {
            Recipe[] recipes = RecipesFactory.getRecipes(url);

            for (Recipe recipe : recipes) {
                list.add(new RecipeCard(recipe));
            }

        } catch (IOException | JSONException e) {
        e.printStackTrace();
        }

        CustomListAdapter adapter = new CustomListAdapter(this, R.layout.activity_recipes_view, list);
        mListView.setAdapter(adapter);
        changeStatusBarColor();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.DKGRAY);
        }
    }


}
