package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView name, username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);

        Intent intent = getIntent();
        String nameVal = intent.getStringExtra("name");
        String usernameVal = intent.getStringExtra("username");
        name.setText(nameVal);
        username.setText(usernameVal);
    }
}