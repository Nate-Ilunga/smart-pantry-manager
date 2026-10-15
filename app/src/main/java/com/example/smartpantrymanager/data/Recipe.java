package com.example.smartpantrymanager.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents one recipe in the app's recipe collection.
 * The ingredients a recipe REQUIRES live in a separate table
 * (see RecipeIngredient) because one recipe has many ingredients.
 */
@Entity(tableName = "recipes")
public class Recipe {

    @PrimaryKey(autoGenerate = true)
    public int id;

    // Display name, e.g. "Chakalaka".
    @NonNull
    public String name;

    // Short description shown on the suggestions list, e.g. "Spicy South African relish".
    public String description;

    // The preparation method, stored as one block of text with numbered steps.
    @NonNull
    public String steps;

    // Rough preparation time in minutes — useful for sorting and for a detail-screen label.
    public int prepTimeMinutes;

    // Room requires a public no-argument constructor.
    public Recipe() {
    }

    public Recipe(@NonNull String name, String description, @NonNull String steps, int prepTimeMinutes) {
        this.name = name;
        this.description = description;
        this.steps = steps;
        this.prepTimeMinutes = prepTimeMinutes;
    }
}