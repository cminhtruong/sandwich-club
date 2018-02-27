package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.description_tv)
    TextView description_tv;
    @BindView(R.id.also_known_tv)
    TextView also_known_tv;
    @BindView(R.id.ingredients_tv)
    TextView ingredients_tv;
    @BindView(R.id.origin_tv)
    TextView origin_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        description_tv.setText(sandwich.getDescription());
        List<String> listName = sandwich.getAlsoKnownAs();
        StringBuilder alsoName = new StringBuilder();
        for (int i = 0; i < listName.size(); i++) {
            if (i > 0) alsoName.append(", ");
            alsoName.append(listName.get(i));
        }
        also_known_tv.setText(alsoName);
        origin_tv.setText(sandwich.getPlaceOfOrigin());
        List<String> listIngredient = sandwich.getIngredients();
        StringBuilder ingredients = new StringBuilder();
        for (int i = 0; i < listIngredient.size(); i++) {
            if (i > 0) ingredients.append(", ");
            ingredients.append(listIngredient.get(i));
        }
        ingredients_tv.setText(ingredients);

        populateUI();
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI() {

    }
}
