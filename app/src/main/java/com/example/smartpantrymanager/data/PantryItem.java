package com.example.smartpantrymanager.data;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents one ingredient the user currently has in their pantry.
 * Each instance of this class is one row in the "pantry_items" table.
 */
@Entity(tableName = "pantry_items")
public class PantryItem {

    // Auto-generated unique ID for each pantry item (the primary key).
    // Room assigns the next number automatically when we insert.
    @PrimaryKey(autoGenerate = true)
    public int id;

    // The ingredient name, e.g. "tomato". Stored in normalised (lowercase) form
    // by our code before saving, so matching later is reliable.
    @NonNull
    public String name;

    // How much of it the user has, e.g. 3.
    public double quantity;

    // The unit for the quantity, e.g. "pcs", "g", "ml".
    @NonNull
    public String unit;

    // Optional expiry date as a simple yyyy-MM-dd string. Null if not set.
    public String expiryDate;

    // Room requires a public no-argument constructor.
    public PantryItem() {
    }

    // Convenience constructor for creating items in code (e.g. seeding/tests).
    public PantryItem(@NonNull String name, double quantity, @NonNull String unit, String expiryDate) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.expiryDate = expiryDate;
    }
}