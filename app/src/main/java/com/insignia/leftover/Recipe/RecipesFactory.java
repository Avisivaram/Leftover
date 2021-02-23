package com.insignia.leftover.Recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.insignia.leftover.Recipe.Recipe;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class RecipesFactory {

    public static JsonArray readJsonFromUrl(String url_string) throws IOException, JSONException {
        // Connect to the URL using java's native library
        URL url = new URL(url_string);
        URLConnection request = url.openConnection();
        request.connect();
        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        return rootobj.getAsJsonArray("hits");
    }

    /**
     * The factory receive a url containing the requested query and using the constructor of Recipe
     * return a list of the recipes objects
     * @param url
     * @return
     * @throws IOException
     * @throws JSONException
     */
    public static Recipe[] getRecipes(String url) throws IOException, JSONException {

        JsonArray recipe_list_json = readJsonFromUrl(url);
        int length = recipe_list_json.size();
        Recipe[] recipe_list_obj = new Recipe[length];
        for (int i = 0; i < length; i++) {
            recipe_list_obj[i] = new Recipe((JsonObject) recipe_list_json.get(i));
        }
        return recipe_list_obj;
    }
}
