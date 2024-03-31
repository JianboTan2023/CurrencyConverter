package com.college.converter.sunlookup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.net.URL;
import java.net.URLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;

public class SearchTime {

    public static String[] search(String lat, String lng)  {
        // Construct the URL for the API request using latitude and longitude
        String url = "https://api.sunrisesunset.io/json?lat=" +lat + "&lng="+lng+"&timezone=UTC&date=today";
        // Load JSON data from the API
        String str= loadJson(url);
        String[] time;
        try {
            // Parse JSON data
            JSONObject jsonObject = new JSONObject(str);
            JSONObject results = jsonObject.optJSONObject("results");
            // Extract sunrise and sunset times
            String sunrise = "Sunrise: "+ results.optString("sunrise");
            String sunset= "Sunset: "+ results.optString("sunset");
            // Store sunrise and sunset times in an array
            time = new String[]{sunrise, sunset};
        } catch (JSONException e) {
            // Throw a runtime exception if there's an error parsing JSON
            throw new RuntimeException(e);
        }
        // Return the array containing sunrise and sunset times
        return time;
    }
    //@SuppressWarnings("deprecation")

    // Method to load JSON data from a given URL
    public static String loadJson(String url) {
        StringBuilder json = new StringBuilder();

        try {
            // Open a connection to the URL
            URL urlobject = new URL(url);
            URLConnection uc = urlobject.openConnection();
            // Read JSON data from the input stream
            BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine = null;
            while ((inputLine = in.readLine()) != null) {
                json.append(inputLine);
            }
            in.close();

        } catch (IOException e) {
            // Throw a runtime exception if there's an error loading JSON data
            throw new RuntimeException(e);
        }
        return json.toString();
    }
}
