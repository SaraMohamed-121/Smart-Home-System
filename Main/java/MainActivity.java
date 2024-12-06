package com.example.vvvvv;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String listTitles[] = {"LIGHT", "FAN", "PASSWORD", "TEMPERATURE", "LCD"};
    int images[] = {R.drawable.led, R.drawable.fan, R.drawable.password, R.drawable.temp, R.drawable.lcd};

    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<ActionItem> itemList = new ArrayList<>();
    List<ActionItem> filteredList = new ArrayList<>();
    TextView lengthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialize the TextView for length
        lengthTextView = findViewById(R.id.lengthTextView);

        // RecyclerView setup
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add items to the list
        for (int i = 0; i < listTitles.length; i++) {
            itemList.add(new ActionItem(listTitles[i], images[i]));
        }

        filteredList.addAll(itemList); // Copy original list
        adapter = new RecyclerViewAdapter(this, filteredList);
        recyclerView.setAdapter(adapter);

        // Update the length text
      //  updateLengthText();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });

        return true;
    }

    private void filter(String text) {
        filteredList.clear();
        for (ActionItem item : itemList) {
            if (item.getTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.notifyDataSetChanged();

        // Update the length text after filtering
       // updateLengthText();
    }

   // private void updateLengthText() {
     //   lengthTextView.setText("Items Length: " + filteredList.size());
    //}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_activity_log) {
            startActivity(new Intent(this, ActivityLogActivity.class));
            return true;
        } else if (id == R.id.action_profile) {
            startActivity(new Intent(this, ProfileActivity.class));
            return true;
        } else if (id == R.id.action_logout) {
            // Handle logout logic
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
