package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.PantryItem;
import com.example.smartpantrymanager.ui.PantryAdapter;
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

        adapter = new PantryAdapter(item -> {
            // Row tapped — opens the edit screen in a later step.
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        ExtendedFloatingActionButton fab = findViewById(R.id.fabAdd);
        fab.setOnClickListener(v -> {
            // Opens Add/Edit screen — wired up next step.
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
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
}