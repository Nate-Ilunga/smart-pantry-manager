package com.example.smartpantrymanager.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object for recipes and their required ingredients.
 * Recipes are seeded once on first run and then read-only in this app,
 * so this DAO is mostly reads plus the inserts used by the seeder.
 */
@Dao
public interface RecipeDao {

    // --- Reads used by the Suggested Recipes and Recipe Detail screens ---

    @Query("SELECT * FROM recipes ORDER BY name ASC")
    List<Recipe> getAllRecipes();

    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    Recipe getRecipeById(int recipeId);

    // All ingredient rows belonging to one recipe.
    // This is the core query the strict-matching algorithm runs per recipe.
    @Query("SELECT * FROM recipe_ingredients WHERE recipeId = :recipeId")
    List<RecipeIngredient> getIngredientsForRecipe(int recipeId);

    // Every ingredient row across all recipes, fetched in one go so the
    // matcher can work in memory instead of hitting the database per recipe.
    @Query("SELECT * FROM recipe_ingredients")
    List<RecipeIngredient> getAllIngredients();

    // --- Inserts used only by the first-run seeder ---

    @Insert
    long insertRecipe(Recipe recipe);

    @Insert
    void insertIngredients(List<RecipeIngredient> ingredients);

    // Used to check whether seeding has already happened.
    @Query("SELECT COUNT(*) FROM recipes")
    int recipeCount();
}