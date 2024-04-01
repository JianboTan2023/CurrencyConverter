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
        @ColumnInfo(name="id")
        String recipeId;

        @ColumnInfo(name = "title")
        String title;
        @ColumnInfo(name = "image")
        String picture;

        @ColumnInfo(name="sourceUrl")
        String sourceUrl
        @ColumnInfo(name = "summary")
        String summary;

//construct with nothing
        public RecipeID() {
        }

        public RecipeID(@NonNull String recipeId, String title, String picture,
                        String sourceUrl, String summary) {
            this.recipeId = recipeId;
            this.title = title;
            this.picture = picture;
            this.sourceUrl = sourceUrl;
            this.summary = summary;

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


        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public String getSourceUrl() {
            return sourceUrl;
        }

        public void setSourceUrl(String sourceUrl) {
            this.sourceUrl = sourceUrl;
        }

         public String getSummary() {
        return summary;
    }

        public void setSummary(String summary) {
        this.summary = summary;
    }


    }

