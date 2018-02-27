package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class JsonUtils {
    private static final String TAG = JsonUtils.class.getSimpleName();

    private static final String NAME = "name";
    private static final String MAINNAME = "mainName";
    private static final String ALSOKNOWAS = "alsoKnownAs";
    private static final String DESCRIPTION = "description";
    private static final String PLACEOFORIGIN = "placeOfOrigin";
    private static final String IMAGE = "image";
    private static final String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject sandwichJSONObject = jsonObject.getJSONObject(NAME);
            sandwich.setMainName(sandwichJSONObject.getString(MAINNAME));
            JSONArray arrayName = (JSONArray) sandwichJSONObject.get(ALSOKNOWAS);
            List<String> nameList = new ArrayList<>();
            if (arrayName != null) {
                for (int i = 0; i < arrayName.length(); i++) {
                    nameList.add(arrayName.getString(i));
                }
            }
            sandwich.setAlsoKnownAs(nameList);
            sandwich.setDescription(jsonObject.getString(DESCRIPTION));
            sandwich.setPlaceOfOrigin(jsonObject.getString(PLACEOFORIGIN));
            sandwich.setImage(jsonObject.getString(IMAGE));
            JSONArray ingreArray = (JSONArray) jsonObject.get(INGREDIENTS);
            List<String> ingredientList = new ArrayList<>();
            if (ingreArray != null) {
                for (int i = 0; i < ingreArray.length(); i++) {
                    ingredientList.add(ingreArray.getString(i));
                }
            }
            sandwich.setIngredients(ingredientList);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseSandwichJson: Error in handling Json data", e);
        }
        return sandwich;
    }
}
