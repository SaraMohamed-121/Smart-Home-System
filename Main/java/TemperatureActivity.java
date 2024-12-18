package com.example.project;
import static com.example.project.HomeActivity.logList;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TemperatureActivity extends AppCompatActivity {
    FirebaseAnalytics analytics;
    private TextView tempValue;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        analytics = FirebaseAnalytics.getInstance(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        Alert.setContext(this);

        tempValue = findViewById(R.id.tempValue);
        databaseReference = FirebaseDatabase.getInstance().getReference("Temperature");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                logList.add("Temperature is changed " + new SimpleDateFormat().format(Calendar.getInstance().getTime()));
                String temperature = snapshot.getValue().toString();
                tempValue.setText(temperature + " °C");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                tempValue.setText("Error loading data");
            }
        });
    }
}
