package com.college.converter.recipe.data;

/**
 * @author Kelly Wu
 *  @lab section 021
 *  this class is to hold RecipeID, which contains more information to be shown at single page
 *  most importantly it has Recipe ID which is used for second requiry
 */

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * items used for database
 */

@Entity(tableName = "RecipeID")
public class RecipeID {

        @PrimaryKey
        @NonNull
        String recipeId;

        @ColumnInfo(name = "title")
        String title;
        @ColumnInfo(name = "picture_small")
        String picture_small;

        @ColumnInfo(name = "picture_big")
         String picture_big;

        @ColumnInfo(name = "ingredient")
        String ingredient;
        @ColumnInfo(name = "instruction")
        String instruction;

//construct with nothing
        public RecipeID() {
        }

        public RecipeID(@NonNull String recipeId, String title, String picture_small, String picture_big,
                        String ingredient, String instruction) {
            this.recipeId = recipeId;
            this.title = title;
            this.picture_small = picture_small;
            this.picture_big = picture_big;
            this.ingredient = ingredient;
            this.instruction = instruction;

        }

        public String getRecipeId() {
            return recipeId;
        }

        public void setRecipeId(String recipeId) {
            this.recipeId = recipeId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }


        public String getPicture_medium() {
            return picture_medium;
        }

        public void setPicture_medium(String picture_medium) {
            this.picture_medium = picture_medium;
        }
        public String getPicture_big() {
        return picture_big;
    }

        public void setPicture_big(String picture_big) {
        this.picture_big = picture_big;
    }

        public String getIngredient() {
            return ingredient;
        }

        public void setIngredient(String ingredient) {
            this.ingredient = ingredient;
        }

         public String getInstruction() {
        return instruction;
    }

        public void setInstruction(String instruction) {
        this.instruction = instruction;
    }


    }

