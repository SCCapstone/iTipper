package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class RecoverPasswordActivity extends AppCompatActivity {
    private EditText recoveryEmail;
    private Button sendEmailButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_password);

        mAuth = FirebaseAuth.getInstance();

        recoveryEmail = findViewById(R.id.recovery_email);
        sendEmailButton = findViewById(R.id.send_recovery_email_button);

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
                                Toast.makeText(context, "Email sent.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(context, LoginActivity.class));
                            }
                            else {
                                String error = Objects.requireNonNull(task.getException()).getMessage();
                                Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
