package com.soob.monthlybudget.databaselayer.database;

import android.content.Context;

import androidx.room.Room;

/**
 * Singleton to create a single instance of the app's database object
 */
public class DatabaseClient
{
    /**
     * App context
     */
    private final Context context;

    /**
     * Static instance of this client - all references to context in this class should call
     * getApplicationContext() to avoid memory leaks that can happen when placing Android context
     * classes in static fields
     */
    private static DatabaseClient instance;

    /**
     * App's database object
     */
    private BudgetDatabase budgetDatabase;

    /**
     * Singleton constructor
     *
     * @param context app context
     */
    private DatabaseClient(Context context)
    {
        // set the context
        this.context = context.getApplicationContext();

        // create the app database with Room database builder
        this.budgetDatabase = Room.databaseBuilder(context.getApplicationContext(),
                BudgetDatabase.class, "BudgetDatabase").fallbackToDestructiveMigration().build();
    }

    /**
     * Get the single instance of the database client. If an instance does not yet exist, a new one
     * will be created
     *
     * @param context app context
     * @return the current instance of the database client
     */
    public static synchronized DatabaseClient getInstance(Context context)
    {
        if (instance == null)
        {
            instance = new DatabaseClient(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Return the database object
     *
     * @return the database object
     */
    public BudgetDatabase getBudgetDatabase()
    {
        return this.budgetDatabase;
    }
}