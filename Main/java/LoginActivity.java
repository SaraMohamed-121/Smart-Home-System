package com.example.project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {
    private EditText email, password;
    private Button login;
    private CheckBox rememberMe;
    private TextView forgotPassword, registerRedirect;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        reference = FirebaseDatabase.getInstance().getReference("users");
        auth = FirebaseAuth.getInstance();
        Database db = new Database(this);

        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        rememberMe = findViewById(R.id.rememberMe);
        forgotPassword = findViewById(R.id.forgotPassword);
        registerRedirect = findViewById(R.id.registerRedirect);

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString();

                if (emailVal.isEmpty()) {
                    email.setError("Please enter your email to reset password");
                }
                else{
                    auth.sendPasswordResetEmail(emailVal).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        registerRedirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailVal = email.getText().toString();
                String passwordVal = password.getText().toString();
                String user = emailVal.split("@")[0];

                if (!emailVal.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailVal).matches()){
                    if (!passwordVal.isEmpty()){
                        if (isInternetAvailable(LoginActivity.this)){
                            auth.signInWithEmailAndPassword(emailVal, passwordVal).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {

                                    reference.child(user).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DataSnapshot> task) {
                                            if (task.isSuccessful()){
                                                if (task.getResult().exists()){
                                                    DataSnapshot data = task.getResult();
                                                    String name_db = String.valueOf(data.child("name").getValue());
                                                    String username_db = String.valueOf(data.child("username").getValue());

                                                    preferences = getSharedPreferences("userData", MODE_PRIVATE);
                                                    editor = preferences.edit();
                                                    editor.putString("name", name_db);
                                                    editor.putString("username", username_db);
                                                    editor.apply();
                                                }
                                            }
                                        }
                                    });
                                    Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            if (db.checkCredentials(emailVal, passwordVal)){
                                String email = user + "@gmail.com";
                                String name_db = db.getName(email);
                                String username_db = db.getUserName(email);

                                preferences = getSharedPreferences("userData", MODE_PRIVATE);
                                editor = preferences.edit();
                                editor.putString("name", name_db);
                                editor.putString("username", username_db);
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else{
                        password.setError("Please enter your password");
                    }
                }
                else if (emailVal.isEmpty()){
                    email.setError("Please enter your email");
                }
                else{
                    email.setError("Please enter a valid email");
                }
            }
        });

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putBoolean("remember", true);
                    editor.apply();
                }
                else if (!buttonView.isChecked()){
                    preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    editor = preferences.edit();
                    editor.putBoolean("remember", false);
                    editor.apply();
                }
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