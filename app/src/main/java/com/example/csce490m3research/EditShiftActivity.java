package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class EditShiftActivity extends AppCompatActivity {
    // Path in database to shift reference that is being edited
    String path;
    DatePicker startDatePicker, endDatePicker;
    TimePicker startTimePicker, endTimePicker;
    Button doneButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shift);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Edit Shift");

        Intent intent = getIntent();

        if (intent.hasExtra("path")) {
            path = intent.getStringExtra("path");
        } else {
            Toast.makeText(this, "Couldn't load shift data.", Toast.LENGTH_LONG);
            startActivity(new Intent(this, ShiftListActivity.class));
        }

        // Initialize and autofill start pickers
        startDatePicker = (DatePicker) findViewById(R.id.startDatePicker);
        startTimePicker = (TimePicker) findViewById(R.id.startTimePicker);

        Date start = new Date(intent.getStringExtra("start"));

        startDatePicker.updateDate(start.getYear()+1900, start.getMonth(), start.getDate());
        startTimePicker.setHour(start.getHours());
        startTimePicker.setMinute(start.getMinutes());

        // Initialize and autofill end pickers
        endDatePicker = (DatePicker) findViewById(R.id.endDatePicker);
        endTimePicker = (TimePicker) findViewById(R.id.endTimePicker);

        Date end = new Date(intent.getStringExtra("end"));

        endDatePicker.updateDate(end.getYear()+1900, end.getMonth(), end.getDate());
        endTimePicker.setHour(end.getHours());
        endTimePicker.setMinute(end.getMinutes());


        doneButton = (Button) findViewById(R.id.done_editing_shift_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startDate = new Date(
                        startDatePicker.getYear() - 1900,
                        startDatePicker.getMonth(),
                        startDatePicker.getDayOfMonth(),
                        startTimePicker.getHour(),
                        startTimePicker.getMinute()
                );
                Date endDate = new Date(
                        endDatePicker.getYear() - 1900,
                        endDatePicker.getMonth(),
                        endDatePicker.getDayOfMonth(),
                        endTimePicker.getHour(),
                        endTimePicker.getMinute()
                );
                Timestamp startTimestamp = new Timestamp(startDate);
                Timestamp endTimestamp = new Timestamp(endDate);

                // Check to make sure the end time is after the start time.
                if (endDate.after(startDate)) {
                    db.document(path).update("start", startTimestamp);
                    db.document(path).update("end", endTimestamp);

                    Toast.makeText(v.getContext(), "Shift edited!", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    String errorText = "End time has to be after start.";
                    Toast.makeText(v.getContext(), errorText, Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
