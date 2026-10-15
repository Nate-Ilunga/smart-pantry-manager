package com.example.smartpantrymanager.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Data Access Object for pantry items.
 * Room generates the implementation of these methods at compile time.
 * This interface is the app's only route to the pantry_items table.
 */
@Dao
public interface PantryDao {

    // CREATE — add a new pantry item. Returns the new row's generated id.
    @Insert
    long insert(PantryItem item);

    // READ — every pantry item, alphabetically by name.
    @Query("SELECT * FROM pantry_items ORDER BY name ASC")
    List<PantryItem> getAll();

    // READ — a single item by its id, used when opening the edit screen.
    @Query("SELECT * FROM pantry_items WHERE id = :itemId LIMIT 1")
    PantryItem getById(int itemId);

    // READ — look up by normalised name. Used by the matching algorithm.
    @Query("SELECT * FROM pantry_items WHERE name = :itemName LIMIT 1")
    PantryItem findByName(String itemName);

    // UPDATE — save changes to an existing item (matched on its primary key).
    @Update
    void update(PantryItem item);

    // DELETE — remove one item.
    @Delete
    void delete(PantryItem item);

    // Convenience count, used to decide whether to show the empty state.
    @Query("SELECT COUNT(*) FROM pantry_items")
    int count();
}