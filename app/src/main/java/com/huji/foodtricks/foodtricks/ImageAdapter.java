package com.huji.foodtricks.foodtricks;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * sets up an image grid
 */
public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private static int INGREDIENT_TEXT_SIZE = 14;

    private static Ingredient[] ingredients ;


    static int INGREDIENTS_AMOUNT;

    private HashMap<Integer, String> ingredientsConversionMap = new HashMap<>();


    private boolean[] isPressed;


    public ImageAdapter(Context c,Ingredient[] arrList) {
        mContext = c;
        ingredients = arrList;
        INGREDIENTS_AMOUNT = ingredients.length;
        isPressed =  new boolean[INGREDIENTS_AMOUNT];
        Arrays.fill(isPressed, Boolean.FALSE);
        initializeConversionMap();
    }

    public int getCount() {
        return ingredients.length;
    }


    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public void setIsPressed(int id) {
        isPressed[id] = !isPressed[id];
    }

    public boolean getIsPressed(int id) {
        return isPressed[id];
    }
    // create a new ImageView for each item referenced by the Adapter

    public View getView(int position, View convertView, ViewGroup parent) {
        View customView;
        LayoutInflater inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView != null) {
            customView = (View) convertView;
            return customView;
        }
        // if it's not recycled, initialize some attributes
        customView = new View(mContext);

        customView = inflater.inflate(R.layout.imagetext_layout, null);
        TextView customText = (TextView) customView.findViewById(R.id.custom_text);
        ImageView customImage = (ImageView) customView.findViewById(R.id.custom_image);
        customText.setText(ingredients[position].getName());
        customText.setTextSize(INGREDIENT_TEXT_SIZE);
        customText.setTypeface(null, Typeface.BOLD);
        customImage.setImageResource(ingredients[position].getIcon());

        return customView;
    }

    public String getIngredientName(int id) {
        return ingredientsConversionMap.get(id);
    }

    private void initializeConversionMap() {
        for (int i = 0; i < INGREDIENTS_AMOUNT; ++i) {
            ingredientsConversionMap.put(i, ingredients[i].getName());
        }
    }
    public void swapItems(Ingredient[] itemsList){
        ingredients = itemsList.clone();
        notifyDataSetChanged();
    }

}
