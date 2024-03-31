


package com.college.converter.sunlookup;

import static com.college.converter.sunlookup.SearchTime.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.college.converter.MainActivity;
import com.college.converter.R;
import com.college.converter.SecondActivity;
import com.college.converter.dictionary.DictionaryActivity;
import com.college.converter.song.ui.SearchArtistActivity;
import com.college.converter.sunlookup.data.ChatMessage;
import com.college.converter.sunlookup.data.ChatMessageDAO;
import com.college.converter.sunlookup.data.ChatViewModel;
import com.college.converter.databinding.ActivitySunBinding;
import com.college.converter.databinding.SentMessageBinding;

import com.college.converter.sunlookup.data.MessageDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import android.view.LayoutInflater;
import android.view.Menu;
import android.content.Context;
import android.content.Intent;

public class SunActivity extends AppCompatActivity {


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId==R.id.help) {

                // Display an AlertDialog with help information when Help menu item is clicked
                AlertDialog.Builder builder = new AlertDialog.Builder(SunActivity.this);
                builder.setMessage(getString(R.string.sunlookup_information));
                builder.setTitle(getString(R.string.sl_ifo_titlr));
                builder.setPositiveButton(getString(R.string.back), (dialog, cl) -> {
                });
                builder.create().show();
                // Show a toast indicating that Help menu item is clicked
                Toast.makeText(this, "CLICK HELP", Toast.LENGTH_SHORT).show();
                return true;}
            else if (itemId==R.id.home){
                // Show a toast indicating that Home menu item is clicked
                Toast.makeText(this, "CLICK HOME", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    String latitude;
    String longitude;
    Handler handler;
    ActivitySunBinding binding;
    ArrayList<ChatMessage> messages = new ArrayList<>();
    private RecyclerView.Adapter<MyRowHolder> myAdapter;
    ChatViewModel chatVM;
    ChatMessageDAO mDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySunBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        MessageDatabase db = Room.databaseBuilder(getApplicationContext(), MessageDatabase.class, "database-name").build();
        mDAO = db.cmDAO();
        chatVM = new ViewModelProvider(this).get(ChatViewModel.class);
        //setSupportActionBar(binding.toolbar);
        messages = chatVM.messages.getValue();

        if(messages == null)
        {
            // If messages list is null, initialize it with an empty ArrayList and post it to LiveData
            chatVM.messages.postValue(messages = new ArrayList<ChatMessage>());
            // Create a new Executor for background thread execution
            Executor thread = Executors.newSingleThreadExecutor();
            thread.execute(() ->
            {   // Retrieve messages from database and add them to the messages list
                messages.addAll( mDAO.getAllMessages() ); //Once you get the data from database
                // Load RecyclerView on the UI thread
                runOnUiThread( () ->  binding.recyclerView.setAdapter( myAdapter )); //You can then load the RecyclerView
            });
        }
        // Retrieve last searched latitude and longitude from SharedPreferences and set them to EditText fields
        SharedPreferences spSearch = getSharedPreferences("searchRecord",MODE_PRIVATE);
        SharedPreferences.Editor edit = spSearch.edit();
        binding.editTextLatitude.setText(spSearch.getString("latSearch","0"));
        binding.editTextLongitude.setText(spSearch.getString("lngSearch","0"));
        // Set OnClickListener for Search button
        binding.buttonSearch.setOnClickListener(clk -> {
            latitude = binding.editTextLatitude.getText().toString();
            longitude = binding.editTextLongitude.getText().toString();
            // Save latitude and longitude to SharedPreferences
            edit.putString("latSearch",latitude);
            edit.putString("lngSearch",longitude);
            edit.apply();
            // Perform search using the provided latitude and longitude
            search(latitude,longitude);

        });
        // Set toolbar title
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("Sun-lookup"); // set the Name
        setSupportActionBar(toolbar);
        // Initialize Handler for receiving messages from background thread
        handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0) {
                    // Update sunrise and sunset text views with received data
                    String[] strData = (String[]) msg.obj;
                    binding.textViewRise.setText(strData[0]);
                    binding.textViewSet.setText(strData[1]);
                    // Show toast message indicating message received
                    Toast.makeText(SunActivity.this, "Get message", Toast.LENGTH_SHORT).show();
                }

            }
        };
        // Set OnClickListener for Save button
        binding.buttonSave.setOnClickListener(click ->{
            // Create a new ChatMessage object and add it to the messages list
            ChatMessage chatmessage1= new ChatMessage(binding.editTextLatitude.getText().toString(),
                    binding.editTextLongitude.getText().toString(),true);
            messages.add(chatmessage1);
            // Insert the message into the database on a background thread
            new Thread(() -> {mDAO.insertMessage(chatmessage1);}).start();
            myAdapter.notifyItemInserted(messages.size()-1);
            //binding.textInput.setText("");
        });
        // Set RecyclerView adapter
        binding.recyclerView.setAdapter(myAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                // Inflate appropriate layout based on message type (sent or received)
                if(viewType == 0) {
                    SentMessageBinding binding = SentMessageBinding.inflate(getLayoutInflater());
                    return new MyRowHolder(binding.getRoot());
                }
                else
                {
                    LayoutInflater row = LayoutInflater.from(parent.getContext());
                    View thisRow = row.inflate(R.layout.receive_message,parent,false);
                    return new MyRowHolder(thisRow);
                }
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                // Bind message data to ViewHolder
                String obj = messages.get(position).getMessage();
                holder.messageText.setText(obj);
                holder.timeText.setText(messages.get(position).getTimeSent());

            }

            @Override
            public int getItemCount() {
                return messages.size();
            }

            public int getItemViewType(int position)
            {
                // Return message type (0 for sent, 1 for received)
                if(messages.get(position).getIsSentButton())
                {
                    return 0;
                }
                else {
                    return 1;
                }
            }
        });

        // Set OnClickListener for Read button
        binding.buttonRead.setOnClickListener(clk ->{
            // Set RecyclerView layout manager to linear layout
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.first_id);

        // Perform item selected listener
        bottomNavigationView.setOnItemSelectedListener(item -> {

            int item_id = item.getItemId();
            if ( item_id == R.id.home_id ) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
            else if (item_id == R.id.first_id) {

                return true;
            }
            else if ( item_id == R.id.second_id ) {
                startActivity(new Intent(getApplicationContext(), SecondActivity.class));
                return true;
            }
            else if ( item_id == R.id.third_id ) {
                startActivity(new Intent(getApplicationContext(), DictionaryActivity.class));
                return true;
            }
            else if ( item_id == R.id.forth_id ) {
                startActivity(new Intent(getApplicationContext(), SearchArtistActivity.class));
                return true;
            }
            return false;
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu resource file to add items to the app bar
        getMenuInflater().inflate(R.menu.sun_menu, menu);
        return true;
    }



// ViewHolder class for RecyclerView
class MyRowHolder extends RecyclerView.ViewHolder {
    TextView messageText;
    TextView timeText;
    public MyRowHolder(@NonNull View itemView) {
        super(itemView);
        // Set OnClickListener for each item in RecyclerView
        itemView.setOnClickListener(clk ->{
            // Get position of clicked item
            int position = getAbsoluteAdapterPosition();
            AlertDialog.Builder builder = new AlertDialog.Builder( SunActivity.this );
            builder.setMessage(getString(R.string.deleted_message));
            builder.setTitle(getString(R.string.question));
            builder.setPositiveButton(getString(R.string.lookup),(dialog,cl)->{
                // Perform a search for latitude and longitude when Lookup option is chosen
                ChatMessage m = messages.get(position);
                latitude = m.getMessage();
                binding.editTextLatitude.setText(latitude);
                longitude = m.getTimeSent();
                binding.editTextLongitude.setText(longitude);
                search(latitude,longitude);

            });
            builder.setNegativeButton(getString(R.string.yes),(dialog,cl)->{
                // Delete message from database and RecyclerView when Yes option is chosen
                ChatMessage m = messages.get(position);
                new Thread(() -> {mDAO.deleteMessage(m);}).start();
                messages.remove(position);
                myAdapter.notifyItemRemoved(position);
                // Show a Snackbar with option to undo deletion
                Snackbar.make(messageText,getString(R.string.deleted_message)+position, Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo),click ->{
                            new Thread(() -> {mDAO.insertMessage(m);}).start();
                            messages.add(position,m);
                            myAdapter.notifyItemInserted(position); }).show();
            });
            builder.create().show();
        });
        // Initialize TextViews for message and time
        messageText = itemView.findViewById(R.id.message);
        timeText =itemView.findViewById(R.id.time);
    }
}
    // Method to perform search for sunrise and sunset times
    public void search(String lat,String lng){
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Create and send a message to mHandler with search results
                String[] time =  SearchTime.search(latitude,longitude);
                // Create and send a message to mHandler with search results
                Message message = new Message();
                message.what = 0;
                message.obj = time;

                handler.sendMessage(message);
            }
        }).start();
    }
}