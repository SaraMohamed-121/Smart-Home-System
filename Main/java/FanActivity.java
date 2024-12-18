package com.example.project;

import static com.example.project.HomeActivity.logList;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;

import com.example.project.R;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.example.project.ActivityLogActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FanActivity extends AppCompatActivity {
    FirebaseAnalytics analytics;
    private ToggleButton fanToggle;
    private DatabaseReference fanRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        analytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);

        Alert.setContext(this);

        fanToggle = findViewById(R.id.fanToggle);
        fanRef = FirebaseDatabase.getInstance().getReference("Fan");

        // Firebase database listener
        fanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                logList.add("Fan status is changed "+new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                Bundle bundle = new Bundle();
                bundle.putString("Fan","Fan status");
                analytics.logEvent("Fan_status_is_changed",bundle);
                Boolean fanStatus = snapshot.getValue(Boolean.class);
                if (fanStatus != null) {
                    fanToggle.setChecked(fanStatus);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle the error if needed
                // Log or show a message for the user
            }
        });

        // Set up listener to update Firebase when the ToggleButton state changes
        fanToggle.setOnCheckedChangeListener((buttonView, isChecked) -> fanRef.setValue(isChecked));
    }
}
