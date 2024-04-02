package com.college.converter.recipe.data;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * @author Kelly Wu
 * @lab section 021
 * this is to hold data for recipe
 */

@Entity(tableName = "recipes")
public class Recipe implements Serializable {

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        protected int id;

        @ColumnInfo(name = "recipeId")
        protected int recipeId;

        @ColumnInfo(name = "title")
        protected String title;

        @ColumnInfo(name = "image_url")
        protected String imageUrl;

        @ColumnInfo(name = "summary")
        protected String summary;

        @ColumnInfo(name = "source_url")
        protected String sourceUrl;

        @ColumnInfo(name = "spoonacular_source_url")
        protected String spoonacularSourceUrl;

        /**
         * Default constructor used by Room to create Recipe instances.
         */
        public Recipe() {}

        /**
         * Constructor used for creating a Recipe instance when fetching from an API.
         *
         */
        public Recipe(int recipeId, String title, String imageUrl) {
                this.recipeId = recipeId;
                this.title = title;
                this.imageUrl = imageUrl;
        }

        /**
         * Full constructor used by Room with all fields.
         *
         */
        @Ignore
        public Recipe(int id, String title, String imageUrl, String summary, String sourceUrl, String spoonacularSourceUrl) {
                this.id = id;
                this.title = title;
                this.imageUrl = imageUrl;
                this.summary = summary;
                this.sourceUrl = sourceUrl;
                this.spoonacularSourceUrl = spoonacularSourceUrl;
        }


        /**
         * getters and setters
         * gets the unique identifier, and retrieve recipeId, title, imageUrl, summary ,
         * and spoonacularSourceUrl from the external source
         */
        public int getId() { return id; }

        public void setId(int id) { this.id = id; }


        public int getRecipeId() {
                return recipeId;
        }


        public void setRecipeId(int recipeId) {
                this.recipeId = recipeId;
        }


        public String getTitle() { return title; }


        public void setTitle(String title) { this.title = title; }


        public String getImageUrl() { return imageUrl; }


        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }


        public String getSummary() { return summary; }


        public void setSummary(String summary) { this.summary = summary; }


        public String getSourceUrl() { return sourceUrl; }


        public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }



        public void setSpoonacularSourceUrl(String spoonacularSourceUrl) {
                this.spoonacularSourceUrl = spoonacularSourceUrl;
        }


        public String getSpoonacularSourceUrl() {
                return spoonacularSourceUrl;
        }
}




