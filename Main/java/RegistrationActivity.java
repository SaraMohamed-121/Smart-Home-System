package com.example.project;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.Calendar;

public class RegistrationActivity extends AppCompatActivity {
    private EditText name, username, email, password, confirmPassword, birthDate;
    private TextView loginRedirect;
    private Button register;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = findViewById(R.id.name);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.confirmPassword);
        birthDate = findViewById(R.id.birthDate);
        register = findViewById(R.id.registerButton);
        loginRedirect = findViewById(R.id.loginRedirect);

        Database db = new Database(this);

        birthDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegistrationActivity.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                    birthDate.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);
                }, year, month, day);

                datePickerDialog.show();
            }
        });

        loginRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameVal = name.getText().toString();
                String usernameVal = username.getText().toString();
                String emailVal = email.getText().toString();
                String passwordVal = password.getText().toString();
                String confirmPasswordVal = confirmPassword.getText().toString();
                String birthDateVal = birthDate.getText().toString();

                if (nameVal.isEmpty() || usernameVal.isEmpty() || emailVal.isEmpty() || passwordVal.isEmpty() || confirmPasswordVal.isEmpty()) {
                    Toast.makeText(RegistrationActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!passwordVal.equals(confirmPasswordVal)) {
                    Toast.makeText(RegistrationActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                auth.createUserWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            String user = emailVal.split("@")[0];
                            db.addUser(nameVal, usernameVal, emailVal, passwordVal, birthDateVal);
                            HelperClass helper = new HelperClass(nameVal, usernameVal, birthDateVal);
                            reference.child(user).setValue(helper);
                            Toast.makeText(RegistrationActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(RegistrationActivity.this, "Registration Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    public boolean isInternetAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
            return capabilities != null &&
                    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
        }
        return false;
    }
}