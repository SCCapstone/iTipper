/** Screen where the user can input tips.
 *
 *  Authors: Paolo Milan, Tyler Chambers
 */
package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;


public class EnterTipScreenActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_tip_screen);
        Button quickTip1 = findViewById(R.id.quicktip1);
        Button quickTip2 = findViewById(R.id.quicktip2);
        Button quickTip3 = findViewById(R.id.quicktip3);
        Button quickTip4 = findViewById(R.id.quicktip4);
        Button quickTip5 = findViewById(R.id.quicktip5);
        responseText = (TextView)findViewById(R.id.responseTextView);

        quickTip1.setText("+ $1");
        quickTip2.setText("+ $2");
        quickTip3.setText("+ $3");
        quickTip4.setText("+ $4");
        quickTip5.setText("+ $5");

    }

    /** Called when the user taps the + button */
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
            String errorText = "Error entering tip: " + message
                    + "\n" + "Tip should be greater than zero.";
            responseText.setText(errorText);
        }
    }

    public void quickRecord(View view) {
        String tipValue = ((Button) view).getText().toString();
        tipValue = tipValue.substring(tipValue.indexOf('$')+1);

        try {
            Tip tip = new Tip(tipValue);
            System.out.println(tip + " from user input");

            Database.writeTip(tip);

            responseText.setText("Entered tip: " + tip.toString());

        } catch (InvalidTipException e) {
            String errorText = "Error entering tip: " + tipValue
                    + "\n" + "Tip should be greater than zero.";
            responseText.setText(errorText);
        }
    }

}
