package com.example.vvvvv;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightActivity extends AppCompatActivity {
    private Switch lightSwitch;
    private DatabaseReference lightRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

       lightSwitch = findViewById(R.id.lightSwitch);
       lightRef = FirebaseDatabase.getInstance().getReference("Light");

        lightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Boolean isLightOn = snapshot.getValue(Boolean.class);
                if (isLightOn != null) {
                    lightSwitch.setChecked(isLightOn);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
        lightSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> lightRef.setValue(isChecked));
    }
}
