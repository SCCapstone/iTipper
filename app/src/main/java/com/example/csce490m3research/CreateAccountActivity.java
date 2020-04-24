package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class CreateAccountActivity extends AppCompatActivity
{


    private EditText UserEmail, UserPassword, UserConfirmPassword;
    private Button CreateAccountButton;
    private ProgressDialog loadingBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Create Account");

        mAuth = FirebaseAuth.getInstance();
        UserEmail = (EditText) findViewById(R.id.register_email);
        UserPassword = (EditText) findViewById(R.id.register_password);
        UserConfirmPassword = (EditText) findViewById(R.id.register_confirm_password);
        CreateAccountButton = (Button) findViewById(R.id.register_create_account);
        loadingBar = new ProgressDialog(this);

        CreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                CreateNewAccount();

            }
        });
    }



    private void SendUserToMainActivity()
    {
        Intent mainIntent = new Intent(CreateAccountActivity.this, LoginActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }


    private void CreateNewAccount()
    {
        String email = UserEmail.getText().toString();
        String password = UserPassword.getText().toString();
        String confirmPassword = UserConfirmPassword.getText().toString();

        if(TextUtils.isEmpty(email))
        {
            Toast.makeText( this, "Please enter an email", Toast.LENGTH_SHORT ).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText( this, "Please enter a password", Toast.LENGTH_SHORT ).show();
        }
        else if(TextUtils.isEmpty(confirmPassword))
        {
            Toast.makeText( this, "Please confirm a your password.", Toast.LENGTH_SHORT ).show();
        }
        else if(!password.equals(confirmPassword))
        {
            Toast.makeText(this, "Passwords do not match!",Toast.LENGTH_SHORT ).show();
        }
        else
        {
            loadingBar.setTitle("Creating account");
            loadingBar.setMessage("Please wait, account is being created");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task)
                        {
                            if(task.isSuccessful())
                            {
                                SendUserToMainActivity();
                                Toast.makeText(CreateAccountActivity.this, "Authentication successful!", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message = task.getException().getMessage();
                                Toast.makeText(CreateAccountActivity.this, "Error Occured: " + message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });
        }
    }


}
