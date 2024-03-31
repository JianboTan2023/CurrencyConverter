package com.college.converter.recipe.data;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "RecipeID")
public class RecipeID {

        @PrimaryKey
        @NonNull
        String recipeId;
        String title;
        @ColumnInfo(name = "title_short")
        String title_short;
        @ColumnInfo(name = "picture_medium")
        String picture_medium;

        @ColumnInfo(name = "ingredient")
        String ingredient;
        @ColumnInfo(name = "instruction")
        String instruction;

        public RecipeID() {
        }

        public RecipeID(@NonNull String recipeId, String title_short, String picture_medium,
                        String ingredient, String instruction) {
            this.recipeId = recipeId;
            this.title_short = title_short;
            this.picture_medium = picture_medium;
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

        public String getTitle_short() {
            return title_short;
        }

        public void setTitle_short(String title_short) {
            this.title_short = title_short;
        }


        public String getPicture_medium() {
            return picture_medium;
        }

        public void setPicture_medium(String picture_medium) {
            this.picture_medium = picture_medium;
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

