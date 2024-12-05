package com.example.vvvvv;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ToggleButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FanActivity extends AppCompatActivity {
    private ToggleButton fanToggle;
    private DatabaseReference fanRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fan);

        fanToggle = findViewById(R.id.fanToggle);
        fanRef = FirebaseDatabase.getInstance().getReference("Fan");

        // Firebase database listener
        fanRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
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
