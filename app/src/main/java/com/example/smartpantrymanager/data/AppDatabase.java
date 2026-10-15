package com.example.smartpantrymanager.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * The app's single Room database.
 * Lists every entity (table) and exposes every DAO.
 * Uses the singleton pattern so the whole app shares one connection —
 * opening multiple database instances is expensive and can cause
 * inconsistent reads.
 */
@Database(
        entities = {PantryItem.class, Recipe.class, RecipeIngredient.class},
        version = 1,
        exportSchema = false
)
public abstract class AppDatabase extends RoomDatabase {

    // Room generates the implementations of these at compile time.
    public abstract PantryDao pantryDao();
    public abstract RecipeDao recipeDao();

    // The one shared instance. "volatile" ensures all threads see the
    // same value immediately after it is assigned.
    private static volatile AppDatabase INSTANCE;

    /**
     * Returns the shared database, creating it on first call.
     * Synchronised so two threads can't both create an instance at once.
     */
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "smart_pantry.db")
                            // Simplification for this project: if the schema
                            // changes, rebuild the database rather than writing
                            // migration code. Acceptable because recipes are
                            // re-seeded and pantry data is user-entered.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}