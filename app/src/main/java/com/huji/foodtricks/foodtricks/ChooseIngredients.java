package com.huji.foodtricks.foodtricks;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.huji.foodtricks.foodtricks.R.color.dot_dark_screen1;
import static com.huji.foodtricks.foodtricks.R.color.dot_dark_screen2;
import static com.huji.foodtricks.foodtricks.R.color.dot_dark_screen3;
import static com.huji.foodtricks.foodtricks.R.color.dot_dark_screen4;
import static com.huji.foodtricks.foodtricks.R.color.dot_light_screen1;
import static com.huji.foodtricks.foodtricks.R.color.dot_light_screen2;
import static com.huji.foodtricks.foodtricks.R.color.dot_light_screen3;
import static com.huji.foodtricks.foodtricks.R.color.dot_light_screen4;
import static com.huji.foodtricks.foodtricks.R.color.dot_light_screen5;
import static com.huji.foodtricks.foodtricks.R.color.material_blue_grey_800;
import static com.huji.foodtricks.foodtricks.R.color.primary_dark_material_dark;


public class ChooseIngredients extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String RECIPE_BASE_URL = "https://api.edamam.com/search?q=";
    private static final String SPACE_CHAR = "%20";
    private static final String API_CREDENTIALS = "&app_id=1b816ee9&app_key=fd31256c4657f51aa2d1edcfb85375fd";
    private static final String LIMIT_RECIPES = "&to=";
    private static final int MAX_RECIPES = 10;
    static final String INGREDIENTS = "Ingredients";
    static final Integer DEFAULT_BACKGROUND = 0;
    static final Integer NUM_OF_COLORS = 5;

    AdapterView<?> mainParent;
    View mainView;
    long mainId;

    public Ingredient[] ingredientsTEST = {
            new Ingredient(R.drawable.pasta, "Pasta"),
            new Ingredient(R.drawable.rice, "Rice"),
            new Ingredient(R.drawable.potatoes, "Potatoes"),
            new Ingredient(R.drawable.flour, "Flour"),
            new Ingredient(R.drawable.cheese, "Cheese"),
            new Ingredient(R.drawable.beef, "Beef"),
            new Ingredient(R.drawable.chicken, "Chicken"),
            new Ingredient(R.drawable.fish, "Fish"),
            new Ingredient(R.drawable.eggs, "Eggs"),
            new Ingredient(R.drawable.beans, "Beans"),
            new Ingredient(R.drawable.broccoli, "Broccoli"),
            new Ingredient(R.drawable.carrot, "Carrot"),
            new Ingredient(R.drawable.cauliflower, "Cauliflower"),
            new Ingredient(R.drawable.leek, "Leek"),
            new Ingredient(R.drawable.mushroom, "Mushroom"),
            new Ingredient(R.drawable.sausage, "Sausage"),
            new Ingredient(R.drawable.tomato, "Tomato"),
            new Ingredient(R.drawable.avocado, "Avocado"),
            new Ingredient(R.drawable.bell_pepper, "Bell Pepper"),
            new Ingredient(R.drawable.cabbage, "Cabbage"),
            new Ingredient(R.drawable.lettuce, "Lettuce"),
            new Ingredient(R.drawable.cucumber, "Cucumber"),
            new Ingredient(R.drawable.spinach, "Spinach"),
            new Ingredient(R.drawable.bacon, "Bacon"),
            new Ingredient(R.drawable.butter, "Butter"),
            new Ingredient(R.drawable.olive_oil, "Olive Oil"),
            new Ingredient(R.drawable.onion, "Onion"),
            new Ingredient(R.drawable.garlic, "Garlic"),
            new Ingredient(R.drawable.chili, "Chili"),
            new Ingredient(R.drawable.lemon, "Lemon"),
            new Ingredient(R.drawable.ginger, "Ginger"),
            new Ingredient(R.drawable.grapes, "Grapes"),
            new Ingredient(R.drawable.apple, "Apple")
    },adapterList;

    ScrollableGridView gridview;
    Spinner search;
    TextView selectedIngredients;
    private IngredientsList ingredientsList;
    private static final Integer[] COLORS = new Integer[NUM_OF_COLORS];


    protected static String buildUrl(ArrayList<String> ingridients) {
        StringBuilder url = new StringBuilder(RECIPE_BASE_URL);
        for (String ingridient : ingridients) {
            url.append(ingridient).append(SPACE_CHAR);
        }
        url.append(API_CREDENTIALS).append(LIMIT_RECIPES).append(MAX_RECIPES);
        System.out.println(url.toString());
        return url.toString();
    }

    protected static String getRecipeStrings(String url) throws IOException {
        // store the information for the matching url query from Adamame API
        try (Scanner scanner = new Scanner(new URL(url).openStream(),
                StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A"); // tokenize the entire string - NEEDED
            return scanner.hasNext() ? scanner.next() : ""; // return query or null
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_choose_ingredients);
        adapterList = ingredientsTEST.clone();
        search = findViewById(R.id.search_ingredients);
        selectedIngredients = findViewById(R.id.selectedIngredients);
        ArrayAdapter<CharSequence> searchAdapter = ArrayAdapter.createFromResource(this,
                R.array.Animals, android.R.layout.simple_spinner_item);
        searchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        search.setAdapter(searchAdapter);
        search.setOnItemSelectedListener(this);
        final ImageAdapter gridAdapter = new ImageAdapter(this, (Ingredient[]) adapterList);
        gridview = (ScrollableGridView) findViewById(R.id.gridview);
        gridview.setAdapter(gridAdapter);
        gridview.setExpanded(true);
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                ImageAdapter adapter = (ImageAdapter) gridview.getAdapter();
                adapter.setIsPressed(position);
                if (position < ImageAdapter.INGREDIENTS_AMOUNT) {
                    mainView = v;
                    if (adapter.getIsPressed(position)) {
                        v.setBackgroundColor(COLORS[position % NUM_OF_COLORS]);
                        ingredientsList.addIngredient(adapter.getIngredientName(position));

                    } else {
                        v.setBackgroundColor(DEFAULT_BACKGROUND);
                        ingredientsList.removeIngredient(adapter.getIngredientName(position));
                    }
                }
            }

            private void gridClick(int position) {

            }
        });

        ingredientsList = new IngredientsList();
        changeStatusBarColor();
        COLORS[0] = getResources().getColor(dot_light_screen1);
        COLORS[1] = getResources().getColor(dot_light_screen2);
        COLORS[2] = getResources().getColor(dot_light_screen3);
        COLORS[3] = getResources().getColor(dot_light_screen4);
        COLORS[4] = getResources().getColor(dot_light_screen5);

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



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_ingredients, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearIngredients() {
        ingredientsList.removeAllIngredients();
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,(Ingredient[]) adapterList));
        gridview.clearChoices();
    }

    public void feedMe(View view) {
        if (ingredientsList.getIngredientsList().size() > 0) {
            Intent feedIntent = new Intent(this, RecipesView.class);
            feedIntent.putExtra(INGREDIENTS, ingredientsList);
            startActivity(feedIntent);
        }
    }

    public void excludeAllIngridients(View view) {
        final GridView gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(this,(Ingredient[]) adapterList));
        gridview.clearChoices();
        ingredientsList.removeAllIngredients();
        selectedIngredients.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        clearIngredients();
    }



    public static Ingredient[] removeTheElement(Ingredient[] arr,
                                         int index)
    {

        // If the array is empty
        // or the index is not in array range
        // return the original array
        if (arr == null
                || index < 0
                || index >= arr.length) {

            return arr;
        }

        // Create another array of size one less
        Ingredient[] anotherArray = new Ingredient[arr.length - 1];

        // Copy the elements from starting till index
        // from original array to the other array
        System.arraycopy(arr, 0, anotherArray, 0, index);

        // Copy the elements from index + 1 till end
        // from original array to the other array
        System.arraycopy(arr, index + 1,
                anotherArray, index,
                arr.length - index - 1);

        // return the resultant array
        return anotherArray;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ImageAdapter adapter = (ImageAdapter) gridview.getAdapter();
        adapter.setIsPressed(position);
        if (position < ImageAdapter.INGREDIENTS_AMOUNT) {
            if (adapter.getIsPressed(position)) {
//                mainView.setBackgroundColor(COLORS[position % NUM_OF_COLORS]);
                ingredientsList.addIngredient(adapter.getIngredientName(position));
                selectedIngredients.append(adapter.getIngredientName(position)+","+" ");

            } else {
//                mainView.setBackgroundColor(DEFAULT_BACKGROUND);
                ingredientsList.removeIngredient(adapter.getIngredientName(position));
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
