package com.example.vvvvv;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class TemperatureActivity extends AppCompatActivity {
    private TextView tempValue;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        tempValue = findViewById(R.id.tempValue);
        databaseReference = FirebaseDatabase.getInstance().getReference("Temperature");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //String temperature ="25";
                String temperature = snapshot.getValue(String.class);
                tempValue.setText(temperature + " °C");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                tempValue.setText("Error loading data");
            }
        });
    }
}
