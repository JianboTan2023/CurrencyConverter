package com.college.converter.recipe.data;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

/**
 * @author Kelly Wu
 * @section Lab 021
 * DAO defines methods for standard database operation, insert, delete, and query,
 * using the room persistence library for SQL abstraction
 */
public interface RecipeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);

    @Delete
    void deleteRecipe(Recipe recipe);

    /**
     * as long as recipes matches @entity table name, the data should be properly inserted
     * @return
     */
    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);
}
