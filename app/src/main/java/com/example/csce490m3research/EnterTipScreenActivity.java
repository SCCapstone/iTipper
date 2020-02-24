/** Screen where the user can input tips.
 *
 *  Authors: Paolo Milan, Tyler Chambers
 */
package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class EnterTipScreenActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    TextView responseText;
    TextView databaseTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_tip_screen);

        responseText = (TextView)findViewById(R.id.responseTextView);

        Database d = new Database();
        d.loadTips(new Callback() {
            @Override
            public void onCallback(List<Tip> tipList) {
                databaseTips = (TextView) findViewById(R.id.tipsView);

                String stringToWrite = "";
                for (Tip t : tipList) {
                    stringToWrite += t.toString() + "\n";
                }

                databaseTips.setText(stringToWrite);
            }
        });
    }

    /** Called when the user taps the Record button */
    public void record(View view) {
        // The string entered by the user in the text box
        EditText editText = findViewById(R.id.tipInputText);
        String message = editText.getText().toString();

        try {
            Tip tip = new Tip(message);
            System.out.println(tip + " from user input");

            Database.writeTip(message);

            editText.setText("");
            responseText.setText("Entered tip: " + tip.toString());

        } catch (InvalidTipException e) {
            responseText.setText("Error entering tip: " + message
                    + ". Tip should be greater than zero.");
        }
    }

}
