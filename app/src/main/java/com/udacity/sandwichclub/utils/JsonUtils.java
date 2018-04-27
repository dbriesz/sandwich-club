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
        JSONObject name = sandwichData.getJSONObject("name");
        String mainName = name.getString("mainName");
        List<String> alsoKnownAs = new ArrayList<>();
        JSONArray alsoKnownAsData = name.getJSONArray("alsoKnownAs");
        if (alsoKnownAsData.length() == 0) {
            alsoKnownAs.add("N/A");
        } else {
            for (int i = 0; i < alsoKnownAsData.length(); i++) {
                alsoKnownAs.add(alsoKnownAsData.getString(i));
            }
        }
        String placeOfOrigin = sandwichData.getString("placeOfOrigin");
        if (placeOfOrigin.equals("")) {
            placeOfOrigin = "Unknown";
        }
        String description = sandwichData.getString("description");
        String image = sandwichData.getString("image");
        List<String> ingredients = new ArrayList<>();
        JSONArray ingredientData = sandwichData.getJSONArray("ingredients");
        for (int i = 0; i < ingredientData.length(); i++) {
            ingredients.add(ingredientData.getString(i));
        }

        return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
    }
}
