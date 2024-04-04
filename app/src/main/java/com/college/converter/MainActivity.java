package com.college.converter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


import com.college.converter.song.ui.SearchArtistActivity;


import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.sunlookup.SunActivity;


/*
    TODOs:
    In groups of 4, complete the following tasks, 1 for each team member:
    1. Extract all the strings into the strings.xml file and use them in the layout and the activity
    2. Change the theme of the app to a NoActionBar theme and modify the primary colors
    3. Add Log messages at the entry/exit of onCreate() and convertCurrency methods. Level should be Info
    4. Add ViewBinding to the project

    ** Each task must be done in a separate branch and merged to the main branch
    after completion using a Pull Request.
    ** Each task must be done by a different team member.

*/

public class MainActivity extends AppCompatActivity {
    static private final Float CONVERSION_RATE = 0.80F;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        final int item_id = item.getItemId();
        if (item_id == R.id.home_id) {
            return true;
        }
        else if (item_id == R.id.first_id) {
            startActivity(new Intent(getApplicationContext(), SunActivity.class));
            return true;
        }
        else if (item_id == R.id.second_id) {
            startActivity(new Intent(getApplicationContext(), ActivityRecipeSearch.class));
            return true;
        }
        else if (item_id == R.id.third_id) {
            startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
            return true;
        }
        else if (item_id == R.id.forth_id) {
            startActivity(new Intent(getApplicationContext(), SearchArtistActivity.class));
            return true;
        }
        return false;


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

     //   BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
// set Toolbar
        setSupportActionBar(findViewById(R.id.mainToolbar));

        // Set Home selected
        //bottomNavigationView.setSelectedItemId(R.id.home_id);

        // Perform item selected listener


    }
}