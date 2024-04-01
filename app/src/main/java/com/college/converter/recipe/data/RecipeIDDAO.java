package com.college.converter.recipe.data;import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
/**
 * Dao interface have three methods insert/query/delete for implementation
 * @author Kelly Wu
 * @lab section 021
 */

public interface RecipeIDDAO {
    @Insert
    long insertRecipeID(RecipeID RecipeID);
    @Query("Select * from RecipeID")
    List<RecipeID> getAllRecipeIDs();
    @Query(("Select * from RecipeID where RecipeID = :id"))
    RecipeID getOneRecipeID(long id);
    @Delete
    void deleteRecipeID(RecipeID RecipeID);
    @Query(("Delete from RecipeID"))
    void deleteAll();
}