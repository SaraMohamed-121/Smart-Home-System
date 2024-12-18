package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Alert {

    private static Context myContext;
    private static Alert instance;
    private static AlertDialog currentAlertDialog;
    private static boolean isAlertVisible = false;

    private static boolean previousAlert = false;
    private static DatabaseReference reference;

    public static void setContext(Context context){
        myContext = context;
    }

    public static void startListening (){
        reference = FirebaseDatabase.getInstance().getReference().child("Alert");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    boolean alert = snapshot.getValue(Boolean.class);

                    if (previousAlert != alert) {
                        previousAlert = alert;
                        Alert.showAlert(alert);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {}
        });
    }

    public static void showAlert(boolean alert) {
        String message = alert ? "We are at Risk!" : "We are Safe!";

        if (currentAlertDialog != null && isAlertVisible) {
            currentAlertDialog.dismiss();
            isAlertVisible = false;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setTitle("Alert")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        if (myContext instanceof Activity && !((Activity) myContext).isFinishing()) {
            currentAlertDialog = builder.create();
            currentAlertDialog.show();
            isAlertVisible = true;
        }
    }
}