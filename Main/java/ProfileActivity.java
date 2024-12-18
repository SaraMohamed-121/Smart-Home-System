package com.example.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Alert.setContext(this);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        String nameVal = preferences.getString("name", "");
        String usernameVal = preferences.getString("username", "");

        name.setText(nameVal);
        username.setText(usernameVal);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}