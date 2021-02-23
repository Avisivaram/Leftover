package com.insignia.leftover.Home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.insignia.leftover.Favourites.FavActivity;
import com.insignia.leftover.Recipe.IngredientsList;
import com.insignia.leftover.R;
import com.insignia.leftover.Recipe.RecipesView;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import java.util.Scanner;

import static com.insignia.leftover.R.color.dot_light_screen1;
import static com.insignia.leftover.R.color.dot_light_screen2;
import static com.insignia.leftover.R.color.dot_light_screen3;
import static com.insignia.leftover.R.color.dot_light_screen4;
import static com.insignia.leftover.R.color.dot_light_screen5;


public class ChooseIngredients extends AppCompatActivity {


    private static final String RECIPE_BASE_URL = "https://api.edamam.com/search?q=";
    private static final String SPACE_CHAR = "%20";
    private static final String API_CREDENTIALS = "&app_id=8c5fad91&app_key=a07093cb238ba63843281ecadbc63371";
    private static final String LIMIT_RECIPES = "&to=";
    private static final int MAX_RECIPES = 10;
    public static final String INGREDIENTS = "Ingredients";
    static final Integer DEFAULT_BACKGROUND = 0;
    static final Integer NUM_OF_COLORS = 5;
    private static final Integer[] COLORS = new Integer[NUM_OF_COLORS];
    int img_count=0;
    Integer[] images = {R.drawable.apple,R.drawable.bacon};

    GridView gridview;



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

    AdapterView<?> mainParent;
    View mainView;
    long mainId;

    Spinner search;
    TextView selectedIngredients;
    private IngredientsList ingredientsList;

    public static String buildUrl(ArrayList<String> ingridients) {
        StringBuilder url = new StringBuilder(RECIPE_BASE_URL);
        for (String ingridient : ingridients) {
            url.append(ingridient).append(SPACE_CHAR);
        }
        url.append(API_CREDENTIALS).append(LIMIT_RECIPES).append(MAX_RECIPES);
        System.out.println(url.toString());
        return url.toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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
        search.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
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
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ImageAdapter gridAdapter = new ImageAdapter(this, (Ingredient[]) adapterList);
        gridview = findViewById(R.id.gridview);
        gridview.setAdapter(gridAdapter);
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


                final ImageButton imgbut = findViewById(R.id.imgButton);
                imgbut.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (img_count < images.length) {
                            imgbut.setImageResource(images[img_count]);
                            img_count++;
                        } else {
                            imgbut.setVisibility(View.GONE);
                        }

                    }
                });
                final ImageButton favbutton = findViewById(R.id.favButton);
                favbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), FavActivity.class));
                    }
                });

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

            private void clearIngredients() {
                ingredientsList.removeAllIngredients();
                final GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new ImageAdapter(getApplicationContext(), (Ingredient[]) adapterList));
                gridview.clearChoices();
            }


            public void feedMe(View view) {
                if (ingredientsList.getIngredientsList().size() > 0) {
                    Intent feedIntent = new Intent(getApplicationContext(), RecipesView.class);
                    feedIntent.putExtra(INGREDIENTS, ingredientsList);

                    startActivity(feedIntent);
                }
            }

            public void excludeAllIngridients(View view) {
                final FloatingActionButton fab = findViewById(R.id.exclude_all_ingridients_fab);
                final GridView gridview = (GridView) findViewById(R.id.gridview);
                gridview.setAdapter(new ImageAdapter(getApplicationContext(), (Ingredient[]) adapterList));
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


        }