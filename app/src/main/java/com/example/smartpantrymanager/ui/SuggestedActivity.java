package com.example.smartpantrymanager.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.SmartPantryApp;
import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.Recipe;
import com.example.smartpantrymanager.data.RecipeMatcher;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
 * Suggested Recipes screen.
 *
 * Runs the strict-matching algorithm against the current pantry and shows
 * only the recipes the user can make right now. Shows an empty-state message
 * when nothing matches.
 */
public class SuggestedActivity extends AppCompatActivity {

    private RecipeAdapter adapter;
    private RecyclerView recycler;
    private View layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        recycler = findViewById(R.id.recyclerRecipes);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        adapter = new RecipeAdapter(recipe -> {
            Intent intent = new Intent(this, RecipeDetailActivity.class);
            intent.putExtra(RecipeDetailActivity.EXTRA_RECIPE_ID, recipe.id);
            startActivity(intent);
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Recompute every time the screen is shown, so changes made to the
        // pantry are reflected immediately.
        loadSuggestions();
    }

    private void loadSuggestions() {
        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);

            // Pull everything the matcher needs in three queries.
            List<Recipe> allRecipes = db.recipeDao().getAllRecipes();
            List<Recipe> makeable = RecipeMatcher.findMakeableRecipes(
                    allRecipes,
                    db.recipeDao().getAllIngredients(),
                    db.pantryDao().getAll());

            runOnUiThread(() -> {
                adapter.setRecipes(makeable);
                boolean empty = makeable.isEmpty();
                layoutEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
                recycler.setVisibility(empty ? View.GONE : View.VISIBLE);
            });
        });
    }
}