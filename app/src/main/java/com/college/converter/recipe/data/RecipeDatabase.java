package com.college.converter.recipe.data;
/**
 * @author Kelly Wu
 *  @lab section 021
 *  this is Database for RecipeID
 */
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeID.class}, version=1)
/**
 * RecipeDatabase extends from RoomDatabase and have abstract class rDAO()
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDao recipeDao();

    // Single instance of the RecipeDatabase
    private static volatile RecipeDatabase INSTANCE;

    /**
     * Retrieves the single instance of the database for the application's context.
     * If the instance does not exist, it synchronously creates the database.
     *
     * @param context The context of the application used to construct the database.
     * @return The single instance of the RecipeDatabase.
     */
    public static RecipeDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RecipeDatabase.class, "recipes_database")
                            .fallbackToDestructiveMigration() // You might want to handle migrations properly instead
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}