package com.example.csce490m3research;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

/** Static class to handle database operations.
 * Write, read, update.
 */
public class Database {

    /** Write a tip out to the database with the current time and signed in user.
     * @param tipValue
     * @throws InvalidTipException: if tipValue <= 0
     */
    public static void writeTip(String tipValue) throws InvalidTipException {
        DatabaseReference tipsRef = tipsReference();

        // Create a reference to a new tip and set it to the current tip
        Tip tip = new Tip(tipValue);
        tipsRef.push().setValue(tip);
    }

    /** Read tips that the user has input to the database and return as an array.
     */
    public static List<Tip> readTips() {
        final List<Tip> tips = new ArrayList<>();

        DatabaseReference tipsRef = tipsReference();
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                    String time = (String) ds.child("time").getValue();
                    String value = (String) ds.child("value").getValue();
                    Tip tip = null;
                    try {
                        tip = new Tip(time, value);
                    } catch (InvalidTipException e) {
                        e.printStackTrace();
                    }
                    tips.add(tip);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        tipsRef.addListenerForSingleValueEvent(eventListener);

        return tips;
    }

    /**
     * @return DatabaseReference for the signed in user's tips
     */
    public static DatabaseReference tipsReference() {
        String userID = getUserID();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tipsRef =
                database.getReference("tips").child(userID);

        return tipsRef;
    }

    /**
     * @return String for the currently signed in user
     */
    public static String getUserID() {
        return FirebaseAuth.getInstance().getUid();
    }
}
