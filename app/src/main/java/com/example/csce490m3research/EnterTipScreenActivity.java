package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/** Screen where the user can input tips.
 *
 *  Authors: Paolo Milan, Tyler Chambers
 */
public class EnterTipScreenActivity extends ToolbarActivity {
    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";

    TextView responseText;
    TextView toastText;
    Toast toast;
    static final int TOAST_TEXT_SIZE = 24;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enter_tip_screen);
        super.onCreate(savedInstanceState);

        setTitle("Shift Screen");

        Button quickTip1 = findViewById(R.id.quicktip1);
        Button quickTip2 = findViewById(R.id.quicktip2);
        Button quickTip3 = findViewById(R.id.quicktip3);
        Button quickTip4 = findViewById(R.id.quicktip4);
        Button quickTip5 = findViewById(R.id.quicktip5);

        final Button startShift = findViewById(R.id.startShiftButton);
        final Button endShift = findViewById(R.id.endShiftButton);

        responseText = findViewById(R.id.responseTextView);

        quickTip1.setText("+ $1");
        quickTip2.setText("+ $2");
        quickTip3.setText("+ $3");
        quickTip4.setText("+ $4");
        quickTip5.setText("+ $5");

        // Check if there's a shift currently running
        Database.getMostRecentShift(new ListCallback() {
            @Override
            public void onCallback(List list) {
                String shiftInfo = "";

                if (!list.isEmpty()) {
                    Shift shift = (Shift) list.get(0);

                    // If the shift's end time is null, a shift is still running
                    if (shift.getEnd() == null && shift.getStart() != null) {
                        shiftInfo = "Current shift started at: " +
                                "\n" + shift.toString();

                        // Don't let the user start a new shift if one is running
                        startShift.setAlpha(.5f);
                        startShift.setClickable(false);
                    }
                    // If both the start and end time exist, the last shift was completed
                    if (shift.getStart() != null && shift.getEnd() != null) {
                        shiftInfo = "Your last shift:\n" +
                                shift.toString();

                        // There's no current shift to end, so disable the end shift button.
                        endShift.setAlpha(.5f);
                        endShift.setClickable(false);
                    }
                }
                else {
                    shiftInfo = "You haven't recorded any shifts yet.";

                    // There's no current shift to end, so disable the end shift button.
                    endShift.setAlpha(.5f);
                    endShift.setClickable(false);
                }
                responseText.setText(shiftInfo);
            }
        });

    }

    /** Called when the user taps the + button */
    public void record(View view) {
        // The string entered by the user in the text box
        EditText editText = findViewById(R.id.tipInputText);
        String message = editText.getText().toString();

        // Clear the user's input after the + button has been pushed
        editText.setText("");

        try {
            Tip tip = new Tip(message);
            System.out.println(tip + " from user input");

            Database.writeTip(message);

            displayTip("" + tip.getValue());

        } catch (InvalidTipException e) {
            String errorText = e.getMessage();
            Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
        } catch (NumberFormatException e) {
            String errorText = "Error: you put in something that wasn't a number.";
            Toast.makeText(this, errorText, Toast.LENGTH_LONG).show();
        }
    }

    public void quickRecord(View view) {
        String tipValue = ((Button) view).getText().toString();
        tipValue = tipValue.substring(tipValue.indexOf('$')+1);

        try {
            Tip tip = new Tip(tipValue);
            System.out.println(tip + " from user input");

            Database.writeTip(tip);

            displayTip(tipValue);

        } catch (InvalidTipException e) {
            String errorText = "Error entering tip: " + tipValue
                    + "\n" + "Tip should be greater than zero.";
            responseText.setText(errorText);
        } catch (NumberFormatException e) {
            responseText.setText(e.getMessage());
        }
    }

    /* When the user taps the "Start shift" button:
     * Insert a shift entry into the database with the current time as the start
     * and the current user's uid as uid
     */
    public void startShift(View view) {
        CollectionReference shifts = Database.shiftsReference();

        // Data to insert into the database
        Map<String,Object> data = new HashMap<>();
        data.put("uid", Database.getUID());
        data.put("start", Timestamp.now());
        data.put("end", null);

        shifts.add(data);

        // Refresh the activity
        finish();
        startActivity(getIntent());
    }

    /* When the user taps the "End shift" button:
     * Find the most recent shift in the database with no end time set,
     * update the end time to the current time.
     */
    public void endShift(View view) {
        Query query = FirebaseFirestore.getInstance()
                .collection("shifts")
                .whereEqualTo("uid", Database.getUID())
                .orderBy("start", Query.Direction.DESCENDING)
                .limit(1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        document.getReference().update("end", Timestamp.now());

                        // Refresh the activity
                        finish();
                        startActivity(getIntent());
                    }
                }
            }
        });
    }

    // Allows the user to view tips entered from tip entry activity
    // Called when user taps "View tips" button
    public void viewTips(View view) {
        //Intent gotoTipsView = new Intent(this, ViewTipsActivity.class);
        Intent gotoTipsView = new Intent(this, TipsListActivity.class);
        startActivity(gotoTipsView);

    }
    public void viewGraph(View view) {
        Intent gotoTipsGraphView = new Intent(this, DisplayTipsGraphActivity.class);
        startActivity(gotoTipsGraphView);
    }
    public void viewShifts(View view) {
        Intent gotoShiftsView = new Intent(this, ShiftListActivity.class);
        startActivity(gotoShiftsView);
    }
    public void viewMaps(View view) {
        Intent gotoMapsView = new Intent(this, MapsActivity.class);
        startActivity(gotoMapsView);
    }

    /**
     * Display a temporary message on the screen to communicate to the user that their tip
     * has been entered.
     * If there's already a message being display, append the message to that message view.
     * @param tip The value of the tip added.
     */
    public void displayTip(String tip) {
        // There's no message currently displayed, so just display the message like normal.
        if (toast == null) {
            String msg = "Added tip: " + tip;
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) toast.getView();

            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(TOAST_TEXT_SIZE);

            toast.show();
        }
        // If a message is already displayed, append to that message instead of making a new one.
        else {
            String msg = ((TextView) ((LinearLayout) toast.getView()).getChildAt(0))
                    .getText().toString();
            msg += ", " + tip;

            toast.cancel();

            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);

            ViewGroup group = (ViewGroup) toast.getView();

            TextView messageTextView = (TextView) group.getChildAt(0);
            messageTextView.setTextSize(TOAST_TEXT_SIZE);

            toast.show();
        }
    }


}
