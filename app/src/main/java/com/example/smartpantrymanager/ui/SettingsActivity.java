package com.example.smartpantrymanager.ui;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.smartpantrymanager.R;
import com.google.android.material.appbar.MaterialToolbar;

/**
 * Settings screen. Lets the user choose the app theme
 * (System default / Light / Dark). The choice is saved to
 * SharedPreferences so it persists across launches, and applied
 * immediately via AppCompatDelegate.
 */
public class SettingsActivity extends AppCompatActivity {

    public static final String PREFS = "settings";
    public static final String KEY_THEME = "theme_mode";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> finish());

        RadioGroup radioTheme = findViewById(R.id.radioTheme);

        // Show the currently-saved choice as selected.
        int savedMode = getThemeMode(this);
        if (savedMode == AppCompatDelegate.MODE_NIGHT_NO) {
            radioTheme.check(R.id.radioLight);
        } else if (savedMode == AppCompatDelegate.MODE_NIGHT_YES) {
            radioTheme.check(R.id.radioDark);
        } else {
            radioTheme.check(R.id.radioSystem);
        }

        // Apply and save whenever the user picks a different option.
        radioTheme.setOnCheckedChangeListener((group, checkedId) -> {
            int mode;
            if (checkedId == R.id.radioLight) {
                mode = AppCompatDelegate.MODE_NIGHT_NO;
            } else if (checkedId == R.id.radioDark) {
                mode = AppCompatDelegate.MODE_NIGHT_YES;
            } else {
                mode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM;
            }
            saveThemeMode(mode);
            AppCompatDelegate.setDefaultNightMode(mode);
        });
    }

    private void saveThemeMode(int mode) {
        getSharedPreferences(PREFS, MODE_PRIVATE)
                .edit()
                .putInt(KEY_THEME, mode)
                .apply();
    }

    /**
     * Reads the saved theme mode, defaulting to "follow system".
     * Static so the Application class can apply it at startup.
     */
    public static int getThemeMode(android.content.Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS, MODE_PRIVATE);
        return prefs.getInt(KEY_THEME, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
    }
}