package com.college.converter.recipe.data;

/**
 * @author Kelly Wu
 * @lab section 021
 * this is to hold data for recipe, it will not contain instructions as it is only used for browsing
 */

public class Recipe {
        String title;
        String image;
        String id;



public String getTitle() {
        return title;
        }

public void setTitle(String recipeTitle) {
        this.title= title;
        }

public String getImage() {
        return image;
        }

public void setImage(String recipePicture) {
        this.image = image;
        }

public String getId() {
        return id;
        }

public void setId(String id) {
        this.id = id;
        }



        }
