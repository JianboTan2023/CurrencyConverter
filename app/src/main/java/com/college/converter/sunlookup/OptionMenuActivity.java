package com.college.converter.sunlookup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.college.converter.R;

/**
 * This file contains the implementation of the OptionMenuActivity Class.
 * This class represents an activity for handling options menu in the application.
 *  It allows users to interact with menu items such as Help and Home.
 *
 * Author: Yue Shang
 * Lab Section: [CST2335_021]
 * Creation Date: [2024-03-31]
 */
public class OptionMenuActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     * Initializes the activity layout.
     *
     * @param savedInstanceState The saved instance state bundle.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option_menu);
    }
    /**
     * Initializes the options menu.
     * Inflates the menu layout from XML resource.
     *
     * @param menu The options menu in which you place your items.
     * @return True if the menu is successfully created, false otherwise.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //binding menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.sun_menu,menu);
        return true;
    }
    /**
     * Handles options menu item selections.
     *
     * @param item The menu item that was selected.
     * @return True if the event was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.help){

                Toast.makeText(this, "CLICK HELP", Toast.LENGTH_SHORT).show();
                return true;}
            else if (itemId==R.id.home) {
                Toast.makeText(this, "CLICK HOME", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}