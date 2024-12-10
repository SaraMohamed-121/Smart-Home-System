package com.example.project;

import static com.example.project.HomeActivity.logList;

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

public class PasswordActivity extends AppCompatActivity {
    FirebaseAnalytics analytics;
    private DatabaseReference passRef;
    private Button passBtn;
    private EditText passTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        analytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        passBtn = findViewById(R.id.password_button);
        passTxt = findViewById(R.id.password_text);
        passRef = FirebaseDatabase.getInstance().getReference("Password");
        passBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logList.add("Password is typed "+new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                Bundle bundle = new Bundle();
                bundle.putString("Password","Password entered");
                analytics.logEvent("Password_entered",bundle);
                passRef.setValue(passTxt.getText().toString());
            }
        });
    }
}