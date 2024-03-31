package com.college.converter.recipe;import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragmentRecipe} factory method to
 * create an instance of this fragment.
 */
public class HomeFragmentRecipe extends Fragment {
    private List<Recipe> listRecipe = new ArrayList<>();
    private JSONArray testArr;
    private RecyclerView myrv;

    private TextView emptyView;
    public String api_key = "6c93a30ed6624a03be850e3d2c118b6b";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @NonNull
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View RootView = inflater.inflate(R.layout.fragment_home, container, false);
        myrv = RootView.findViewById(R.id.recyclerview);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getRandomRecipes();

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        // Inflate the layout for this fragment
        return RootView;
    }
    private void getRandomRecipes() {
        String URL = " https://api.spoonacular.com/recipes/random?number=40&apiKey="+api_key;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            testArr = (JSONArray) response.get("recipes");
                            Log.i("the res is:", String.valueOf(testArr));
                            for (int i = 0; i < testArr.length(); i++) {
                                JSONObject jsonObject1;
                                jsonObject1 = testArr.getJSONObject(i);
                                listRecipe.add(new Recipe(jsonObject1.optString("id"),
                                        jsonObject1.optString("title"), jsonObject1.optString("image"),
                                        Integer.parseInt(jsonObject1.optString("servings")),
                                        Integer.parseInt(jsonObject1.optString("readyInMinutes" ))));
                            }
                            RecyclerViewAdapterRecipe myAdapter = new RecyclerViewAdapterRecipe(getContext(), listRecipe);
                            myrv.setAdapter(myAdapter);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("the res is error:", error.toString());
                        //progressBar.setVisibility(View.GONE);
                        myrv.setAlpha(0);
                        emptyView.setVisibility(View.VISIBLE);
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}