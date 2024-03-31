package com.college.converter.recipe.data;import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {RecipeID.class}, version=1)
/**
 * RecipeDatabase extends from RoomDatabase and have abstract class rDAO()
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public abstract class RecipeIDDatabase extends RoomDatabase {

    public abstract RecipeIDDAO recipeIDDAO();
}