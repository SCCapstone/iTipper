package com.example.csce490m3research;


import android.app.Activity;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {
    Button button;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        final Intent mainMenu = new Intent(this, MainMenuActivity.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();

                    startActivity(mainMenu);

                }
            }
        });
    }

    public void gotoMainMenu(View view) {
        Intent mainMenu = new Intent(this, MapsDialogueActivity.class);
        startActivity(mainMenu);
    }
}