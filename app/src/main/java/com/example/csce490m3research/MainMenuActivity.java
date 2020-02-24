package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.SetOptions;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MainMenuActivity extends AppCompatActivity {

    String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        db.setFirestoreSettings(settings);

        UID = FirebaseAuth.getInstance().getUid();

        DocumentReference userDoc = db.collection("users").document(UID);

        Map<String, Object> loginTimeStamp = new HashMap<>();

        java.sql.Timestamp time = new java.sql.Timestamp(System.currentTimeMillis());
        loginTimeStamp.put("last_login", time);

        userDoc.set(loginTimeStamp, SetOptions.merge());
    }

    // Called when user taps "Maps" button
    public void mapDialog(View view) {
        //Intent gotoMapDialog = new Intent(this, MapsDialogActivity.class);
        //startActivity(gotoMapDialog);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    // Called when user taps "Graph" button
    public void graphDisplay(View view) {
        Intent gotoGraphDisplay = new Intent(this, ChooseGraphActivity.class);
        startActivity(gotoGraphDisplay);
    }

    // Called when user taps "Enter tip" button
    public void enterTips(View view) {
        Intent gotoTips = new Intent(this, EnterTipScreenActivity.class);
        startActivity(gotoTips);
    }
}
