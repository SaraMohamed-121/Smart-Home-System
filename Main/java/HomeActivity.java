package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static ArrayList<String> logList = new ArrayList<>();
    String listTitles[] = {"LIGHT", "FAN", "PASSWORD", "TEMPERATURE", "LCD"};
    int images[] = {R.drawable.led, R.drawable.fan, R.drawable.password, R.drawable.temp, R.drawable.lcd};
    Database db = new Database(this);
    RecyclerView recyclerView;
    RecyclerViewAdapter adapter;
    List<ActionItem> itemList = new ArrayList<>();
    List<ActionItem> filteredList = new ArrayList<>();
    TextView lengthTextView;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        reference = FirebaseDatabase.getInstance().getReference("users");

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
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_activity_log) {
            logList.add("action_activity_log" + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
            startActivity(new Intent(this, ActivityLogActivity.class));
            return true;
        }
        else if (id == R.id.action_profile) {
            logList.add("action_profile" + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
            Intent i = getIntent();
            String user = i.getStringExtra("user");
            if (isInternetAvailable(HomeActivity.this)){
                reference.child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (task.isSuccessful()){
                            if (task.getResult().exists()){
                                DataSnapshot data = task.getResult();
                                String name_db = String.valueOf(data.child("name").getValue());
                                String username_db = String.valueOf(data.child("username").getValue());

                                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                                intent.putExtra("name", name_db);
                                intent.putExtra("username", username_db);
                                startActivity(intent);
                            }
                        }
                    }
                });
            }
            else{
                String email = user + "@gmail.com";
                String name_db = db.getName(email);
                String username_db = db.getUserName(email);
                Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("name", name_db);
                intent.putExtra("username", username_db);
                startActivity(intent);
            }
            return true;
        } else if (id == R.id.action_logout) {
            logList.add("action_logout" + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
            SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("remember", "false");
            editor.apply();
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, LoginActivity.class));
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }
}
