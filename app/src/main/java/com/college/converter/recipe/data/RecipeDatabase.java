package com.college.converter.recipe.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


/**
 * RecipeDatabase extends from RoomDatabase and have abstract class RecipeDAO()
 * DAO contains methods to insert and delete operations on the database, and maintain the database
 * @author Kelly Wu
 * @date: March 30, 2024
 */
@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    public abstract RecipeDAO recipeDAO();

    // Single instance of the RecipeDatabase
    private static volatile RecipeDatabase INSTANCE;

    /**
     * Retrieves the single instance of the database if already existed.It will insert data if not existed
     * @param context construct the database.
     * @return The single instance.
     */
    public static RecipeDatabase getDbInstance(Context context) {
        if (INSTANCE == null) {
            //this part ensures only one thread can enter the critical section at a time
            synchronized (RecipeDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    RecipeDatabase.class, "recipes_database")
                            .fallbackToDestructiveMigration() //this is a destructive migration strategy
                            .build(); //build the database instance based on the configuration
                }
            }
        }
        //returns the singleton instance of RecipeDatabase to the caller
        return INSTANCE;
    }
}