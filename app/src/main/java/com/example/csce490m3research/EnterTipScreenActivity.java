// Added by @paolomilan
package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EnterTipScreenActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_tip_screen);
    }

    /** Called when the user taps the Record button */
    public void record(View view) {
        // The string entered by the user in the text box
        EditText editText = findViewById(R.id.tipInputText);
        String message = editText.getText().toString();

        // Write the tip out to the database
        try {
            Database.writeTip(message);
        } catch (InvalidTipException e) {
            //TODO: send an error message to the user that they've entered an invalid tip
            e.printStackTrace();
        }

    }
}
