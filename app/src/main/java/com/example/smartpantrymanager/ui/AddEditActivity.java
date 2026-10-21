package com.example.smartpantrymanager.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.SmartPantryApp;
import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.PantryItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

/**
 * Add or edit a single pantry item.
 *
 * Opened with no extra data to ADD a new item, or with an item id passed
 * via Intent to EDIT an existing one. The same form serves both.
 */
public class AddEditActivity extends AppCompatActivity {

    // Key used to pass an item id into this screen. Public so MainActivity can use it.
    public static final String EXTRA_ITEM_ID = "extra_item_id";

    private static final String[] UNITS = {"pcs", "g", "kg", "ml", "l", "tsp", "tbsp", "cup"};

    private TextInputEditText inputName, inputQuantity, inputExpiry;
    private AutoCompleteTextView inputUnit;

    // -1 means "adding new"; any other value is the id of the item being edited.
    private int editingItemId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        inputName = findViewById(R.id.inputName);
        inputQuantity = findViewById(R.id.inputQuantity);
        inputUnit = findViewById(R.id.inputUnit);
        inputExpiry = findViewById(R.id.inputExpiry);

        // Populate the unit dropdown.
        inputUnit.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, UNITS));

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        // If an item id was passed in, we are editing — load that item.
        editingItemId = getIntent().getIntExtra(EXTRA_ITEM_ID, -1);
        if (editingItemId != -1) {
            toolbar.setTitle("Edit item");
            loadExistingItem(editingItemId);
        }

        MaterialButton buttonSave = findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(v -> saveItem());
    }

    private void loadExistingItem(int id) {
        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            PantryItem item = db.pantryDao().getById(id);
            if (item == null) return;

            runOnUiThread(() -> {
                inputName.setText(item.name);
                inputQuantity.setText(trimZero(item.quantity));
                inputUnit.setText(item.unit, false);
                inputExpiry.setText(item.expiryDate);
            });
        });
    }

    /**
     * Validates the form, then inserts or updates the item in the database.
     */
    private void saveItem() {
        String name = text(inputName);
        String quantityText = text(inputQuantity);
        String unit = text(inputUnit);
        String expiry = text(inputExpiry);

        // --- Input validation ---
        if (name.isEmpty()) {
            inputName.setError("Please enter an ingredient name");
            inputName.requestFocus();
            return;
        }
        if (quantityText.isEmpty()) {
            inputQuantity.setError("Please enter a quantity");
            inputQuantity.requestFocus();
            return;
        }
        double quantity;
        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            inputQuantity.setError("Quantity must be a number");
            inputQuantity.requestFocus();
            return;
        }
        if (quantity <= 0) {
            inputQuantity.setError("Quantity must be greater than zero");
            inputQuantity.requestFocus();
            return;
        }
        if (unit.isEmpty()) {
            Toast.makeText(this, "Please choose a unit", Toast.LENGTH_SHORT).show();
            return;
        }

        // Normalise the name so matching is reliable later.
        final String normalisedName = name.trim().toLowerCase();
        final String finalUnit = unit.trim().toLowerCase();
        final String finalExpiry = expiry.isEmpty() ? null : expiry.trim();
        final double finalQuantity = quantity;

        SmartPantryApp.getDatabaseExecutor().execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);

            if (editingItemId == -1) {
                // Adding a new item.
                PantryItem item = new PantryItem(normalisedName, finalQuantity, finalUnit, finalExpiry);
                db.pantryDao().insert(item);
            } else {
                // Updating the existing item.
                PantryItem item = db.pantryDao().getById(editingItemId);
                if (item != null) {
                    item.name = normalisedName;
                    item.quantity = finalQuantity;
                    item.unit = finalUnit;
                    item.expiryDate = finalExpiry;
                    db.pantryDao().update(item);
                }
            }

            runOnUiThread(this::finish);
        });
    }

    // --- Small helpers ---

    private String text(android.widget.EditText field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }

    private String text(AutoCompleteTextView field) {
        return field.getText() == null ? "" : field.getText().toString().trim();
    }

    private String trimZero(double value) {
        if (value == Math.floor(value)) return String.valueOf((int) value);
        return String.valueOf(value);
    }
}