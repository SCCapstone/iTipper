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

public class LoginActivity extends Activity {
    Button button;
    EditText username, password;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        button = (Button) findViewById(R.id.button);
        username = (EditText) findViewById(R.id.user);
        password = (EditText) findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) {
                    Toast.makeText(getApplicationContext(),
                            "Redirecting...", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}










        setContentView(R.layout.activity_login);
    }

    // Called when user taps "login" button
    public void login(View view) {
        // Send user to main menu screen
        Intent mainMenu = new Intent(this, MainMenuActivity.class);
        startActivity(mainMenu);
    }
}
