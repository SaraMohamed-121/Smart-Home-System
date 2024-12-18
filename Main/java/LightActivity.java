package com.example.project;

import static com.example.project.HomeActivity.logList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Switch;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LightActivity extends AppCompatActivity {
    FirebaseAnalytics analytics;

    private Switch lightSwitch;
    private DatabaseReference reference;
    private DatabaseReference lightRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        analytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light);

        Alert.setContext(this);

        lightSwitch = findViewById(R.id.lightSwitch);
        lightRef = FirebaseDatabase.getInstance().getReference("Light");

        lightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Bundle bundle = new Bundle();
                bundle.putString("led","led status");
                analytics.logEvent("led_status_is_changed",bundle);
                Boolean isLightOn = snapshot.getValue(Boolean.class);
                logList.add("Light status is changed " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
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
