package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import java.util.Objects;

/**
 * Class allows user to edit the tip values and updates firestore with the new data
 */
public class EditTipActivity extends ToolbarActivity {
    String path;

    DatePicker tipDatePicker;
    TimePicker tipTimePicker;
    EditText tipEditValue;

    Button doneButton;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_tip);
        super.onCreate(savedInstanceState);

        setTitle("Edit Tip");

        Intent intent = getIntent();

        if (intent.hasExtra("path")) {
            path = intent.getStringExtra("path");
        } else {
            Toast.makeText(this, "Couldn't load tip data.", Toast.LENGTH_LONG)
                .show();

            startActivity(new Intent(this, ShiftListActivity.class));
        }

        tipDatePicker = findViewById(R.id.tipDatePicker);
        tipTimePicker = findViewById(R.id.tipTimePicker);
        tipEditValue = findViewById(R.id.tipEditValue);

        // Initialize edit value to whatever the tip is in the database
        tipEditValue.setText(intent.getStringExtra("value"));

        // Initialize the date and time picker, too
        Date date = new Date(intent.getStringExtra("date"));

        tipDatePicker.updateDate(date.getYear()+1900, date.getMonth(), date.getDate());
        tipTimePicker.setHour(date.getHours());
        tipTimePicker.setMinute(date.getMinutes());

        final Date oldDate = getPickerDate();
        double oldVal = 0;
        try {
            oldVal = new Tip(getTipEditValue()).getValue();
        } catch (InvalidTipException e) {
            e.printStackTrace();
        }

        doneButton = findViewById(R.id.done_editing_tip_button);
        final double finalOldVal = oldVal;
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Use try to check for valid tip value.
                try {
                    if (!tipEditValue.getText().toString().isEmpty()) {
                        Date newDate = getPickerDate();
                        Timestamp newTimestamp = new Timestamp(newDate);
                        double newVal = new Tip(tipEditValue.getText().toString()).getValue();

                        /* If nothing has been edited, don't send update request to database
                           and don't send "Tip edited!" message to user.
                           Just exit the activity. */
                        if (newVal == finalOldVal && newDate.equals(oldDate)) {
                            finish();
                        } else {
                        /* If either the value or date has changed, send "Tip edited!" message,
                           but only send update request for fields that changed. */
                            if (!(newVal == finalOldVal)) {
                                Tip tip = new Tip(tipEditValue.getText().toString());
                                db.document(path).update("value", tip.getValue());
                            }
                            if (!newDate.equals(oldDate)) {
                                db.document(path).update("time", newTimestamp);
                            }

                            // Finally, exit the activity and inform the user data has changed.
                            Toast.makeText(v.getContext(), "Tip edited!", Toast.LENGTH_SHORT)
                                    .show();
                            finish();
                        }
                    }
                } catch (InvalidTipException e) {
                    e.printStackTrace();
                    Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    String errText =
                            "You entered something that couldn't be interpreted as a number.";
                    Toast.makeText(v.getContext(), errText, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Form a Date from the values currently displayed in the date and time picker.
     * @return Date corresponding to date and time on each picker on the screen
     */
    Date getPickerDate() {

        return new Date(
                tipDatePicker.getYear() - 1900,
                tipDatePicker.getMonth(),
                tipDatePicker.getDayOfMonth(),
                tipTimePicker.getHour(),
                tipTimePicker.getMinute()
        );
    }

    /**
     * Grab the string currently in the Edit Value text box.
     * @return String currently in Edit Value edit text view
     */
    String getTipEditValue() {
        return tipEditValue.getText().toString();
    }
}
