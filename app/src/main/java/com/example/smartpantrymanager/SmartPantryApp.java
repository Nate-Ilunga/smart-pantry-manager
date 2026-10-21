package com.example.smartpantrymanager;

import android.app.Application;

import com.example.smartpantrymanager.data.AppDatabase;
import com.example.smartpantrymanager.data.DatabaseSeeder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Application class for Smart Pantry Manager.
 *
 * Android creates one instance of this when the app process starts,
 * before any Activity. That makes it the right place for one-time setup
 * such as seeding the recipe collection.
 */
public class SmartPantryApp extends Application {

    // A single background thread shared across the app for database work.
    // Room refuses database access on the main thread because it would
    // freeze the user interface.
    private static final ExecutorService databaseExecutor =
            Executors.newSingleThreadExecutor();

    @Override
    public void onCreate() {
        super.onCreate();

        // Seed the recipe collection off the main thread.
        // DatabaseSeeder checks whether recipes already exist, so this
        // is safe to call on every launch.
        databaseExecutor.execute(() -> {
            AppDatabase db = AppDatabase.getInstance(this);
            DatabaseSeeder.seedIfEmpty(db);
        });
    }

    /**
     * Shared background executor for database reads and writes.
     * Screens use this instead of creating their own threads.
     */
    public static ExecutorService getDatabaseExecutor() {
        return databaseExecutor;
    }
}