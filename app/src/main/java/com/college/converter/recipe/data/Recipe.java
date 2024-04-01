package com.college.converter.recipe.data;

/**
 * @author Kelly Wu
 * @lab section 021
 * this is to hold data for recipe, it will not contain instructions as it is only used for browsing
 */

public class Recipe {
        String recipeTitle;
        String recipePicture;
        String recipeId;



public String getRecipeTitle() {
        return recipeTitle;
        }

public void setRecipeTitle(String recipeTitle) {
        this.recipeTitle = recipeTitle;
        }

public String getRecipePicture() {
        return recipePicture;
        }

public void setRecipePicture(String recipePicture) {
        this.recipePicture = recipePicture;
        }

public String getRecipeId() {
        return recipeId;
        }

public void setRecipeId(String recipeId) {
        this.recipeId = recipeId;
        }



        }
