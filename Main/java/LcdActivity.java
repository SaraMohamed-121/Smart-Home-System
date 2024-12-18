package com.example.project;

import static com.example.project.HomeActivity.logList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LcdActivity extends AppCompatActivity {
    FirebaseAnalytics analytics;
    private DatabaseReference lcdRef;
    private Button lcdBtn;
    private EditText lcdTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lcd);

        Alert.setContext(this);

        analytics = FirebaseAnalytics.getInstance(this);
        lcdBtn = findViewById(R.id.lcd_button);
        lcdTxt = findViewById(R.id.lcd_message);
        lcdRef = FirebaseDatabase.getInstance().getReference("Lcd");

        lcdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logList.add("Lcd Message is typed " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                Bundle bundle = new Bundle();
                bundle.putString("Lcd","Lcd Message");
                analytics.logEvent("Lcd_Message",bundle);
                lcdRef.setValue(lcdTxt.getText().toString());
                lcdTxt.setText("");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}