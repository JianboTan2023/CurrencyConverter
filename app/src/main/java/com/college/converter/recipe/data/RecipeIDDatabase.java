package com.college.converter.recipe.data;
/**
 * @author Kelly Wu
 *  @lab section 021
 *  this is Database for RecipeID
 */
import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.college.converter.dictionary.WordRecord;

@Database(entities = {RecipeID.class}, version=1)
/**
 * RecipeDatabase extends from RoomDatabase and have abstract class rDAO()
 * @author Kelly Wu
 * @date: March 30, 2024
 */
public abstract class RecipeIDDatabase extends RoomDatabase {

    public abstract RecipeIDDAO RecipeIDDAO();
}