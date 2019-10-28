// coded by John Esco, iTipper
package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.example.CSCE490M3Research.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    // redirects to google maps on button press
    public void sendMessage(View view) {
        // Do something in response to button
        // create new intent object (Context, Class) this activity is subclass of context. Class is where system delivers Intent
        //Intent intent = new Intent(this, DisplayMessageActivity.class);
        Intent intent = new Intent(this, MapsActivity.class);
        // this finds the plain text named editText
        EditText editText = (EditText) findViewById(R.id.editText);
        // retrieve text that was entered
        String message = editText.getText().toString();
        // put extra add the value of EditText to the intent. Intent can carry data types as key/val pairs called extras
        intent.putExtra(EXTRA_MESSAGE,message);
        startActivity(intent);
    }
}
