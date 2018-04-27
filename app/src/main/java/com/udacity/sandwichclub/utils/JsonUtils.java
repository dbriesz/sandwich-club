package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {
        JSONObject sandwichData = new JSONObject(json);
        String mainName = String.valueOf(sandwichData.getJSONObject("main_name"));
        List<String> alsoKnownAs = new ArrayList<>();
        JSONArray alsoKnownAsData = sandwichData.getJSONArray("alsoKnownAs");
        for (int i = 0; i < alsoKnownAsData.length(); i++) {
            alsoKnownAs.add(alsoKnownAsData.getString(i));
        }
        String placeOfOrigin = String.valueOf(sandwichData.getJSONObject("placeOfOrigin"));
        String description = String.valueOf(sandwichData.getJSONObject("description"));
        String image = String.valueOf(sandwichData.getJSONObject("image"));
        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientData = sandwichData.getJSONArray("ingredients");
        for (int i = 0; i < ingredientData.length(); i++) {
            alsoKnownAs.add(ingredientData.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
