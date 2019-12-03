package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    // Called when user taps "Maps" button
    public void mapDialog(View view) {
        Intent gotoMapDialog = new Intent(this, MapsDialogueActivity.class);
        startActivity(gotoMapDialog);
    }

    // Called when user taps "Graph" button
    public void graphDisplay(View view) {
        Intent gotoGraphDisplay = new Intent(this, DisplayGraphActivity.class);
        startActivity(gotoGraphDisplay);
    }
}
