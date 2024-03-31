package com.college.converter.recipe.data;import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
/**
 * Dao interface have three methods insert/query/delete for implementation
 * @author Kelly Wu
 */
public interface RecipeIDDAO {
    @Insert
    long insertRecipeID(RecipeID recipeId);
    @Query("Select * from RecipeID")
    List<RecipeID> getAllRecipeIDs();
    @Query(("Select * from RecipeID where recipeId = :id"))
    RecipeID getOneRecipeID(long recipeId);
    @Delete
    void deleteRecipeID(RecipeID recipeId);
    @Query(("Delete from RecipeID"))
    void deleteAll();
}