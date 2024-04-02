package com.college.converter.dictionary;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.college.converter.FirstActivity;
import com.college.converter.ForthActivity;
import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.R.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.college.converter.SecondActivity;
import com.college.converter.databinding.ActivityDictionaryBinding;
import com.college.converter.recipe.ui.RecipeSearchActivity;
import com.college.converter.song.ui.SearchArtistActivity;
import com.college.converter.sunlookup.SunActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.android.volley.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class DictionaryActivity extends AppCompatActivity {
    ActivityDictionaryBinding binding;
    protected String searchWord;
    ArrayList<WordRecord> dictRecords;
    ArrayList<String> wordDefinitions =new ArrayList<>();
    DictionaryDAO dictionaryDAO;
    RecyclerView.Adapter myWordAdapter;
    RecyclerView.Adapter myDefinitionAdapter;
    SharedPreferences prefs;
    String result;
    String totalResult;
    private final String TAG = getClass().getSimpleName();
    private final  String URL_REQUEST = "https://api.dictionaryapi.dev/api/v2/entries/en/";
    private final String front= "{\"words\":";
    private final String back= "}";

    protected RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDictionaryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        DictionaryDatabase db= Room.databaseBuilder(getApplicationContext(), DictionaryDatabase.class, getString(string.word_database_name))
                .build();
        dictionaryDAO = db.dicDAO();

        setSupportActionBar(binding.toolbar);

        queue= Volley.newRequestQueue(this);

        if (dictRecords == null) {
            dictRecords = new ArrayList<>();
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {
                dictRecords.addAll( dictionaryDAO.getAllRecords() ); //get the data from database
                runOnUiThread( () ->  binding.recycleWordHistory.setAdapter(myWordAdapter)); //load the RecyclerView
            });
        }

        prefs = getSharedPreferences(getString(string.word_sharedPreferences_name), Context.MODE_PRIVATE);
        String previous = prefs.getString(getString(string.word_search), "");
        binding.editTextWord.setText(previous);

        binding.recycleWordHistory.setAdapter(myWordAdapter =new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                    LayoutInflater newRow = LayoutInflater.from(parent.getContext());
                    View thisRow = newRow.inflate(R.layout.word,parent,false);
                    return new MyRowHolder(thisRow);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                WordRecord obj = dictRecords.get(position);
                holder.wordText.setText(obj.getWord());
            }

            @Override
            public int getItemCount() {
                return dictRecords.size();
            }
        });

        binding.recycleViewDefinitions.setAdapter(myDefinitionAdapter =new RecyclerView.Adapter<MyRowHolder2>() {
            @NonNull
            @Override
            public MyRowHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {

                LayoutInflater newRow = LayoutInflater.from(parent.getContext());
                View thisRow = newRow.inflate(layout.definition,parent,false);
                return new MyRowHolder2(thisRow);
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder2 holder, int position) {
                String obj = wordDefinitions.get(position);
                holder.resultText.setText(obj);
            }

            @Override
            public int getItemCount() {
                return wordDefinitions.size();
            }
        });

        binding.recycleViewDefinitions.setLayoutManager(new LinearLayoutManager(this));

        binding.buttonSearch.setOnClickListener(click -> {
            searchWord =binding.editTextWord.getText().toString();
            try{
              if(!searchWord.isEmpty()){
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString(getString(string.word_search), searchWord);
                editor.apply();

                String url= URL_REQUEST + URLEncoder.encode(searchWord,"UTF-8");
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        (response) -> {
                            String jsonString = front + response + back;
                            StringBuilder resultBuilder = new StringBuilder();

                            try {
                                JSONObject jsonObject = new JSONObject(jsonString);
                                JSONArray wordsArray = jsonObject.getJSONArray("words");
                                for (int i = 0; i < wordsArray.length(); i++) {
                                    JSONObject wordsItem = wordsArray.getJSONObject(i);

                                    JSONArray meaningsArray = wordsItem.getJSONArray("meanings");

                                    for (int j = 0; j < meaningsArray.length(); j++) {
                                        JSONObject meaningsItem = meaningsArray.getJSONObject(j);

                                        JSONArray definitionsArray = meaningsItem.getJSONArray("definitions");
                                        for (int k = 0; k < definitionsArray.length(); k++) {
                                            JSONObject definitionsItem = definitionsArray.getJSONObject(k);
                                            result =  definitionsItem.getString("definition");
                                            resultBuilder.append(result); // Append current definition to StringBuilder
                                            resultBuilder.append("\n");
//                                            resultRecord.add(result);
//                                            myAdapter2.notifyItemInserted(resultRecord.size() - 1);
                                        }
                                    }
                                }
                                totalResult = resultBuilder.toString();
                                showMeanings(totalResult);

                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                        },
                        (error) -> {
                            Log.e(TAG, R.string.error + error.getMessage());
                        });
                queue.add(stringRequest);
            }
        }
        catch (Exception e) {
            Log.e(TAG, getString(string.error_word_info));
        }

        });

        binding.buttonSave.setOnClickListener(click -> {
            WordRecord DR=new WordRecord(searchWord, totalResult);
            dictRecords.add(DR);
            new Thread(() -> {dictionaryDAO.insertRecord(DR);}).start();
 //           myAdapter.notifyItemInserted(records.size() - 1);
            binding.editTextWord.setText("");
//            binding.result.setText("");
        });

        binding.buttonRead.setOnClickListener(click -> {
            binding.recycleWordHistory.setLayoutManager(new LinearLayoutManager(this));
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.third_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int item_id = item.getItemId();
            if ( item_id == R.id.home_id ) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else if (item_id == R.id.first_id) {
                startActivity(new Intent(getApplicationContext(), SunActivity.class));
                return true;
            }
            else if ( item_id == R.id.second_id ) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                return true;
            }
            else if ( item_id == R.id.third_id ) {
                startActivity(new Intent(getApplicationContext(), RecipeSearchActivity.class));
                return true;
            }
            else if ( item_id == R.id.forth_id ) {
                startActivity(new Intent(getApplicationContext(), SearchArtistActivity.class));
                return true;
            }
            return false;
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.dictionary_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if ( id ==  R.id.help) {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(DictionaryActivity.this);
            builder1.setMessage(getString(string.dictionary_information));
            builder1.setTitle(getString(string.dictionary_info_title));

            builder1.create().show();
        }
        else if (id ==  R.id.home) {
            Toast.makeText(this, getString(string.back), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView wordText;

        public MyRowHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                String word = dictRecords.get(position).getWord();
                binding.editTextWord.setText(word);
                String meaning = dictRecords.get(position).getDefinitions();
                showMeanings(meaning);

              
            });
            itemView.setOnLongClickListener(click -> {
                int position = getAbsoluteAdapterPosition();
                AlertDialog.Builder builder = new AlertDialog.Builder(DictionaryActivity.this);
                builder.setMessage(getString(string.want_to_delete) + wordText.getText());
                builder.setTitle(getString(string.question));
                builder.setPositiveButton(getString(string.yes), (dialog, cl) -> {
                    WordRecord removedRecord= dictRecords.get(position);
                    new Thread(() -> {dictionaryDAO.deleteRecord(removedRecord);}).start();
                    dictRecords.remove(position);
                    myWordAdapter.notifyItemRemoved(position);

                    String editText= binding.editTextWord.getText().toString();
                    String historyWord= wordText.getText().toString();
                    if(editText.equals(historyWord)){
                        binding.editTextWord.setText("");
                        wordDefinitions.clear();
                        myDefinitionAdapter.notifyDataSetChanged();
                    }
                    
                    Snackbar.make(wordText,getString(string.deleted_message)+position, Snackbar.LENGTH_LONG)
                            .setAction(getString(string.undo), clk->{
                                new Thread(() -> {dictionaryDAO.insertRecord(removedRecord);}).start();
                                dictRecords.add(position,removedRecord);
                                myWordAdapter.notifyItemInserted(position);


                                if(editText.equals(historyWord)){
                                    binding.editTextWord.setText(editText);
                                    showMeanings(removedRecord.getDefinitions());
                                }

                            }).show();
                });
                builder.setNegativeButton(getString(string.no), (dialog, cl) -> {
                });
                builder.create().show();
                return false;
            });
            wordText = itemView.findViewById(R.id.record);
        }
   }

    /**
     * show Meanings of a word on recycleViewDef
     * @param meaning
     */
    private void showMeanings(String meaning) {
        //clear all item in recycleViewDef
        wordDefinitions.clear();
        myDefinitionAdapter.notifyDataSetChanged();

        // update recycleViewDef with current meaning

        String[] definitions = meaning.split("\n");

        for (String s : definitions) {
            this.wordDefinitions.add(s);

        }
        myDefinitionAdapter.notifyDataSetChanged();
    }

    class MyRowHolder2 extends RecyclerView.ViewHolder {
        TextView resultText;
        public MyRowHolder2(@NonNull View itemView) {
            super(itemView);
            resultText = itemView.findViewById(R.id.record);
        }
    }

    /**
     * Called when activity start-up is complete (after onStart() and onRestoreInstanceState(Bundle) have been called).
     * refer to: https://blog.csdn.net/ylbf_dev/article/details/80125016
     * @param savedInstanceState
     */
    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Toolbar title and subtitle
        if (binding.toolbar != null) {
            binding.toolbar.setTitle(R.string.word_activity_name);
            binding.toolbar.setSubtitle(string.app_name);
        }
    }

}