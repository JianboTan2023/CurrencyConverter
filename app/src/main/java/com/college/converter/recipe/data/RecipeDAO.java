package com.college.converter.recipe.data;

import androidx.room.Dao;
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
@Dao
public interface RecipeDAO {

    //insert a recipe with the same ID as an existing one and will be replaced
    //by using onConflict
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe recipe);
//delet a recipe from the data source
    @Delete
    void deleteRecipe(Recipe recipe);
/**
 * below psection execute SQL query that selects all recipes from the recipes table
 */
    /**
     * as long as recipes matches @entity table name, the data should be properly inserted
     * @return
     */
    @Query("SELECT * FROM recipes")
    List<Recipe> getAllRecipes();
    @Query("SELECT * FROM recipes WHERE id = :recipeId")
    Recipe getRecipeById(int recipeId);
}
