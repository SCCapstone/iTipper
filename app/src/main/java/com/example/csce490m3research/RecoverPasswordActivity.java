package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

/**
 * Screen where the user can have a new password sent to the email they signed up with via
 * Firebase Auth, in case they forget.
 * Screen elements include a text box where the user can input their email, and a button
 * to grab the contexts of that box to attempt to use for password recovery.
 * Written by: Tyler Chambers
 */
public class RecoverPasswordActivity extends AppCompatActivity {
    private EditText recoveryEmail;
    private Button sendEmailButton;
    private FirebaseAuth mAuth;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        mAuth = FirebaseAuth.getInstance();

        recoveryEmail = findViewById(R.id.recovery_email);
        sendEmailButton = findViewById(R.id.send_recovery_email_button);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Recover Password");

        final Context context = this;

        sendEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = recoveryEmail.getText().toString();

                if (email.isEmpty()) {
                    finish();
                }
                else {
                    mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(context, "Email sent.", Toast.LENGTH_LONG)
                                        .show();

                                startActivity(new Intent(context, LoginActivity.class));
                            }
                            else {
                                String error = Objects
                                        .requireNonNull(task.getException())
                                        .getMessage();

                                Toast.makeText(context, error, Toast.LENGTH_LONG)
                                        .show();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Define actions to perform when an item on the screen's action bar is selected by the user.
     * @param item The element on the action bar that the user selected
     * @return true if successful, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
