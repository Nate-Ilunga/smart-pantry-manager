package com.example.smartpantrymanager.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 * Represents ONE ingredient that a recipe requires.
 * A recipe with 6 ingredients has 6 rows in this table, all sharing the same recipeId.
 * This is the "many" side of a one-to-many relationship with Recipe.
 */
@Entity(
        tableName = "recipe_ingredients",
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipeId",
                onDelete = ForeignKey.CASCADE
        ),
        indices = {@Index("recipeId")}
)
public class RecipeIngredient {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Links this ingredient row back to its parent recipe.
    public int recipeId;

    // Ingredient name in normalised (lowercase, singular) form, e.g. "tomato".
    @NonNull
    public String name;

    // How much the recipe needs, e.g. 2.
    public double quantity;

    // Unit for the quantity, e.g. "pcs", "g", "ml".
    @NonNull
    public String unit;

    // Room requires a public no-argument constructor.
    public RecipeIngredient() {
    }

    public RecipeIngredient(int recipeId, @NonNull String name, double quantity, @NonNull String unit) {
        this.recipeId = recipeId;
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }
}