package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.SmartPantryApp;
import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.Recipe;
import com.example.smartpantrymanager.data.RecipeIngredient;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
 * Shows the full detail for one recipe: description, prep time,
 * its required ingredients, and the preparation method.
 *
 * The recipe id is passed in via Intent; the screen then loads the
 * recipe and its ingredients from the database on a background thread.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    private TextView textName, textDescription, textTime, textIngredients, textSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        textName = findViewById(R.id.textName);
        textDescription = findViewById(R.id.textDescription);
        textTime = findViewById(R.id.textTime);
        textIngredients = findViewById(R.id.textIngredients);
        textSteps = findViewById(R.id.textSteps);

        int recipeId = getIntent().getIntExtra(EXTRA_RECIPE_ID, -1);
        if (recipeId == -1) {
            finish();  // nothing to show
            return;
        }

        loadRecipe(recipeId);
    }

    private void loadRecipe(int recipeId) {
        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            Recipe recipe = db.recipeDao().getRecipeById(recipeId);
            List<RecipeIngredient> ingredients =
                    db.recipeDao().getIngredientsForRecipe(recipeId);

            if (recipe == null) {
                runOnUiThread(this::finish);
                return;
            }

            // Build the ingredient list as one text block, one per line.
            StringBuilder ingredientText = new StringBuilder();
            for (RecipeIngredient ing : ingredients) {
                ingredientText.append("• ")
                        .append(formatQuantity(ing.quantity))
                        .append(" ").append(ing.unit)
                        .append("  ").append(capitalise(ing.name))
                        .append("\n");
            }

            runOnUiThread(() -> {
                textName.setText(recipe.name);
                textDescription.setText(recipe.description);
                textTime.setText(recipe.prepTimeMinutes + " min");
                textIngredients.setText(ingredientText.toString().trim());
                textSteps.setText(recipe.steps);
            });
        });
    }

    private String capitalise(String text) {
        if (text == null || text.isEmpty()) return "";
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    private String formatQuantity(double value) {
        if (value == Math.floor(value)) return String.valueOf((int) value);
        return String.valueOf(value);
    }
}
