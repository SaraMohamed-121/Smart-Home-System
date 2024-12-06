package com.example.vvvvv;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActionActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        String title = getIntent().getStringExtra("title");
        String subtitle = getIntent().getStringExtra("subtitle");

        TextView titleView = findViewById(R.id.actionTitle);
        TextView subtitleView = findViewById(R.id.actionSubtitle);

        titleView.setText(title);
        subtitleView.setText(subtitle);
    }
}
