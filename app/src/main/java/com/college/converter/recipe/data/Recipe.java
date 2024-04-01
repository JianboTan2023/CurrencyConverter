package com.college.converter.recipe.data;

/**
 * @author Kelly Wu
 * @lab section 021
 * this is to hold data for recipe, it will not contain instructions as it is only used for browsing
 */

public class Recipe {
        String recipeName;
        String recipePicture_small;
        String ingredient;



public String getRecipeName() {
        return recipeName;
        }

public void setRecipeName(String recipeName) {
        this.recipeName=recipeName;
        }

public String getRecipePicture_small() {
        return recipePicture_small;
        }

public void setRecipePicture_small(String recipePicture_small) {
        this.recipePicture_small = recipePicture_small;
        }

public String getIngredient() {
        return ingredient;
        }

public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
        }



        }
