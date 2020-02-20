// Added by @paolomilan
package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import static androidx.constraintlayout.widget.Constraints.TAG;


public class EnterTipScreenActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    TextView tipsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_tip_screen);

        tipsView = new TextView(this);
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

        tipsView.setText(userTips());
    }

    public String userTips() {
        String tipsString = "";
        List<Tip> tips = Database.readTips();
        Log.i(TAG, "Tips entered: " + tips.size());

        for (Tip t: tips) {
            tipsString += t.toString();
        }

        Log.i(TAG, tipsString);
        return tipsString;
    }
}
