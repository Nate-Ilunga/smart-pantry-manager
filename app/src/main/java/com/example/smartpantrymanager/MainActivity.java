package com.example.smartpantrymanager;
import com.example.smartpantrymanager.ui.SettingsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.PantryItem;
import com.example.smartpantrymanager.ui.AddEditActivity;
import com.example.smartpantrymanager.ui.PantryAdapter;
import com.example.smartpantrymanager.ui.SuggestedActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PantryAdapter adapter;
    private RecyclerView recycler;
    private View layoutEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recyclerPantry);
        layoutEmpty = findViewById(R.id.layoutEmpty);

        // Set up the pantry list. Tapping a row opens it in the edit screen.
        adapter = new PantryAdapter(new PantryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PantryItem item) {
                // Tap a row → edit it.
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(AddEditActivity.EXTRA_ITEM_ID, item.id);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(PantryItem item) {
                // Long-press a row → confirm, then delete.
                confirmDelete(item);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        // Add button opens the add screen with no data (add mode).
        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v ->
                startActivity(new Intent(this, AddEditActivity.class)));

        // Toolbar menu. The search icon opens the Suggested Recipes screen.
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_suggested) {
                startActivity(new Intent(this, SuggestedActivity.class));
                return true;
            } else if (id == R.id.action_settings) {
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload whenever the screen is shown so edits/additions appear immediately.
        loadPantryItems();
    }

    private void loadPantryItems() {
        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            List<PantryItem> items = db.pantryDao().getAll();

            runOnUiThread(() -> {
                adapter.setItems(items);
                boolean empty = items.isEmpty();
                layoutEmpty.setVisibility(empty ? View.VISIBLE : View.GONE);
                recycler.setVisibility(empty ? View.GONE : View.VISIBLE);
            });
        });
    }

    private void confirmDelete(PantryItem item) {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Delete item")
                .setMessage("Remove " + item.name + " from your pantry?")
                .setPositiveButton("Delete", (dialog, which) -> deleteItem(item))
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void deleteItem(PantryItem item) {
        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            db.pantryDao().delete(item);
            runOnUiThread(this::loadPantryItems);  // reload the list after deleting
        });
    }
}