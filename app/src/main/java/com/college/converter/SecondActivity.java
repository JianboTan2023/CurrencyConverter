package com.college.converter;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.second_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item ->
            {
                int item_id = item.getItemId();
                if ( item_id == R.id.home_id ) {
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                }
                else if (item_id == R.id.first_id) {
                    startActivity(new Intent(getApplicationContext(), FirstActivity.class));
                    return true;
                }
                else if ( item_id == R.id.second_id ) {
                    return true;
                }
                else if ( item_id == R.id.third_id ) {
                    startActivity(new Intent(getApplicationContext(), Dictionary.class));
                    return true;
                }
                else if ( item_id == R.id.forth_id ) {
                    startActivity(new Intent(getApplicationContext(), ForthActivity.class));
                    return true;
                }
                return false;
            }
        );

    }
}