package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class EditTipActivity extends AppCompatActivity {
    String path;

    DatePicker tipDatePicker;
    TimePicker tipTimePicker;
    EditText tipEditValue;

    Button doneButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tip);

        Intent intent = getIntent();

        if (intent.hasExtra("path")) {
            path = intent.getStringExtra("path");
        } else {
            Toast.makeText(this, "Couldn't load tip data.", Toast.LENGTH_LONG);
            startActivity(new Intent(this, ShiftListActivity.class));
        }

        tipDatePicker = (DatePicker) findViewById(R.id.tipDatePicker);
        tipTimePicker = (TimePicker) findViewById(R.id.tipTimePicker);
        tipEditValue = (EditText) findViewById(R.id.tipEditValue);

        // Initialize edit value to whatever the tip is in the database
        tipEditValue.setText(intent.getStringExtra("value"));

        // Initialize the date and time picker, too
        Date date = new Date(intent.getStringExtra("date"));
        System.out.println(date);
        System.out.println(date.getYear());
        System.out.println(date.getMonth());
        System.out.println(date.getDate());
        tipDatePicker.updateDate(date.getYear()+1900, date.getMonth(), date.getDate());
        tipTimePicker.setHour(date.getHours());
        tipTimePicker.setMinute(date.getMinutes());

        doneButton = (Button) findViewById(R.id.done_editing_tip_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date tipDate = new Date(
                        tipDatePicker.getYear() - 1900,
                        tipDatePicker.getMonth(),
                        tipDatePicker.getDayOfMonth(),
                        tipTimePicker.getHour(),
                        tipTimePicker.getMinute()
                );
                Timestamp tipTimestamp = new Timestamp(tipDate);

                // Check for valid tip value
                try {
                    if (!tipEditValue.getText().toString().isEmpty()) {
                        Tip tip = new Tip(tipEditValue.getText().toString());
                        db.document(path).update("value", tip.getValue());
                    }
                    db.document(path).update("time", tipTimestamp);
                } catch (InvalidTipException e) {
                    e.printStackTrace();
                }
                Toast.makeText(v.getContext(), "Tip edited!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
