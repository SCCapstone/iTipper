package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.common.graph.Graph;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

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

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_settings:
                            selectedFragment = new SettingsFragment();
                            break;
                        case R.id.nav_graph:
                            selectedFragment = new GraphFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    // Called when user taps "Maps" button
    public void mapDialog(View view) {
        //Intent gotoMapDialog = new Intent(this, MapsDialogActivity.class);
        //startActivity(gotoMapDialog);
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

    // Called when user taps "Graph" button
    public void graphDisplay(View view) {
        //Intent gotoGraphDisplay = new Intent(this, ChooseGraphActivity.class);
        //startActivity(gotoGraphDisplay);
        // Go directly to graph
        Intent gotoTipsGraph = new Intent(this, DisplayTipsGraphActivity.class);
        startActivity(gotoTipsGraph);
    }

    // Called when user taps "Enter tip" button
    public void enterTips(View view) {
        Intent gotoTips = new Intent(this, EnterTipScreenActivity.class);
        startActivity(gotoTips);
    }

    // Called when user taps "View tips" button
    public void viewTips(View view) {
        Intent gotoTipsView = new Intent(this, TipsListActivity.class);
        startActivity(gotoTipsView);
    }

    // Called when user taps "View shifts" button
    public void viewShifts(View view) {
        Intent gotoTipsView = new Intent(this, ShiftListActivity.class);
        startActivity(gotoTipsView);
    }
}
