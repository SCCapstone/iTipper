package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Called when user taps "login" button
    public void login(View view) {
        // Send user to main menu screen
        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
    }
}
