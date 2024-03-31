package com.college.converter.recipe;import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
/**
 * Dao interface have three methods insert/query/delete for implementation
 * @author Kelly Wu
 */
public interface RecipeDAO {
    @Insert
    long insertRecipe(RecipePhoto r);

    @Query("Select * from RecipePhoto")
    List<RecipePhoto> getAllrecipes();

    @Delete
    void deleteRecipe(RecipePhoto r);
}