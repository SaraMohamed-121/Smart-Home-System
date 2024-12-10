package com.example.project;

import static com.example.project.HomeActivity.logList;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Detected_Person_Alert extends AppCompatActivity {
    FirebaseAnalytics analytics;
    DatabaseReference Alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detected_person_alert);
        Alert = FirebaseDatabase.getInstance().getReference("alert");
        Alert.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Bundle bundle = new Bundle();
                bundle.putString("Person","Person Detected");
                analytics.logEvent("Person_Detected",bundle);
                Boolean Person = snapshot.getValue(Boolean.class);
                if (Person == true) {
                    logList.add("Person is detected "+new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                    Toast.makeText(Detected_Person_Alert.this, "We are at Risk!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Detected_Person_Alert.this, "We are at Safe!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}