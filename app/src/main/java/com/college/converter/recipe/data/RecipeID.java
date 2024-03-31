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
        @ColumnInfo(name = "picture_big")
        String picture_big;
        @ColumnInfo(name = "ingredient")
        String ingredient;

        public RecipeID() {
        }

        public RecipeID(@NonNull String recipeId, String title_short, String picture_medium, String picture_big, String ingredient) {
            this.recipeId = recipeId;
            this.title_short = title_short;
            this.picture_medium = picture_medium;
            this.picture_big = picture_big;
            this.ingredient = ingredient;

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

        public String getPicture_big() {
            return picture_big;
        }

        public void setPicture_big(String picture_big) {
            this.picture_big = picture_big;
        }
    }

