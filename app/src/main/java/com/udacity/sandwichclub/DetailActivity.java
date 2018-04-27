package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mSandwichImage;
    private TextView mSandwichOrigin;
    private TextView mSandwichAlsoKnownAs;
    private TextView mSandwichDescription;
    private TextView mSandwichIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        Sandwich sandwich = null;
        try {
            sandwich = JsonUtils.parseSandwichJson(json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        mSandwichImage = (ImageView) findViewById(R.id.image_iv);
        mSandwichOrigin = (TextView) findViewById(R.id.origin_tv);
        mSandwichAlsoKnownAs = (TextView) findViewById(R.id.also_known_tv);
        mSandwichDescription = (TextView) findViewById(R.id.description_tv);
        mSandwichIngredients = (TextView) findViewById(R.id.ingredients_tv);

        mSandwichOrigin.setText(sandwich.getPlaceOfOrigin());
        int count = 0;
        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();
        List<String> dedupedAlsoKnownAsList = new ArrayList<>();
        for (String str : alsoKnownAsList) {
            if (!dedupedAlsoKnownAsList.contains(str)) {
                dedupedAlsoKnownAsList.add(str);
            }
        }
        for (String i : dedupedAlsoKnownAsList) {
            if(count++ == dedupedAlsoKnownAsList.size() - 1) {
                mSandwichAlsoKnownAs.append(i);
            } else {
                mSandwichAlsoKnownAs.append(i + ", ");
                count++;
            }
        }
        mSandwichDescription.setText(sandwich.getDescription());
        int counter = 0;
        for (String j : sandwich.getIngredients()) {
            if(counter++ == sandwich.getIngredients().size() - 1) {
                mSandwichIngredients.append(j);
            } else {
                mSandwichIngredients.append(j + ", ");
                count++;
            }
        }

        mSandwichImage.setVisibility(View.VISIBLE);
        mSandwichOrigin.setVisibility(View.VISIBLE);
        mSandwichAlsoKnownAs.setVisibility(View.VISIBLE);
        mSandwichDescription.setVisibility(View.VISIBLE);
        mSandwichIngredients.setVisibility(View.VISIBLE);
    }
}
