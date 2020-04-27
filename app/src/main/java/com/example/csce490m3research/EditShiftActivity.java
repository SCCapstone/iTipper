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
import java.util.Objects;

/**
 * Class allows user to edit the time a shift started or ended and updates firestore with the new data
 */
public class EditShiftActivity extends ToolbarActivity {
    // Path in database to shift reference that is being edited
    String path;
    DatePicker startDatePicker, endDatePicker;
    TimePicker startTimePicker, endTimePicker;
    Button doneButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_shift);
        super.onCreate(savedInstanceState);

        setTitle("Edit Shift");

        Intent intent = getIntent();

        if (intent.hasExtra("path")) {
            path = intent.getStringExtra("path");
        } else {
            Toast.makeText(this, "Couldn't load shift data.", Toast.LENGTH_LONG)
                    .show();

            startActivity(new Intent(this, ShiftListActivity.class));
        }

        // Initialize and autofill start pickers
        startDatePicker = findViewById(R.id.startDatePicker);
        startTimePicker = findViewById(R.id.startTimePicker);

        Date start = new Date(intent.getStringExtra("start"));

        startDatePicker.updateDate(start.getYear()+1900, start.getMonth(), start.getDate());
        startTimePicker.setHour(start.getHours());
        startTimePicker.setMinute(start.getMinutes());

        // Initialize and autofill end pickers
        endDatePicker = findViewById(R.id.endDatePicker);
        endTimePicker = findViewById(R.id.endTimePicker);

        if (intent.hasExtra("end")) {
            Date end = new Date(intent.getStringExtra("end"));

            endDatePicker.updateDate(end.getYear()+1900, end.getMonth(), end.getDate());
            endTimePicker.setHour(end.getHours());
            endTimePicker.setMinute(end.getMinutes());
        } else {
            endDatePicker.setEnabled(false);
            endTimePicker.setEnabled(false);
        }

        final Date oldStart = getStartPickerDate();
        final Date oldEnd = getEndPickerDate();

        doneButton = findViewById(R.id.done_editing_shift_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date startDate = getStartPickerDate();
                Date endDate = getEndPickerDate();

                /* If nothing changed, just finish activity without sending any update requests
                   to database. */
                if (startDate.equals(oldStart) && endDate.equals(oldEnd)) {
                    finish();
                }
                else {
                    Timestamp startTimestamp = new Timestamp(startDate);
                    Timestamp endTimestamp = new Timestamp(endDate);

                    // Check to make sure the end time is after the start time.
                    if (endDate.after(startDate)) {
                        db.document(path).update("start", startTimestamp);

                        // Don't use the end picker if it's disabled (indicating the shift is ongoing)
                        if (endDatePicker.isEnabled() && endTimePicker.isEnabled()) {
                            db.document(path).update("end", endTimestamp);
                        }

                        Toast.makeText(v.getContext(), "Shift edited!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        if (endTimePicker.isEnabled() && endDatePicker.isEnabled()) {
                            String errorText = "End time has to be after start.";
                            Toast.makeText(v.getContext(), errorText, Toast.LENGTH_LONG).show();
                        } else {
                            String errorText = "Can't edit the start of the shift into the future!";
                            Toast.makeText(v.getContext(), errorText, Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        });
    }

    /**
     *
     * @return Date currently represented by the Start pickers
     */
    Date getStartPickerDate() {

        return new Date(
                startDatePicker.getYear() - 1900,
                startDatePicker.getMonth(),
                startDatePicker.getDayOfMonth(),
                startTimePicker.getHour(),
                startTimePicker.getMinute()
        );
    }

    Date getEndPickerDate() {

        return new Date(
                endDatePicker.getYear() - 1900,
                endDatePicker.getMonth(),
                endDatePicker.getDayOfMonth(),
                endTimePicker.getHour(),
                endTimePicker.getMinute()
        );
    }
}
