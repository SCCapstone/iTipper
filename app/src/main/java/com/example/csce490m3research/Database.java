/** Static class to handle database operations.
 *  Should allow writing tip values, reading the tips that have been written
 *
 *  Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import android.graphics.Color;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYSeries;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Database {

    private static FirebaseFirestore mFirestore;

    /** Write a tip out to the database with the current time and signed in user.
     * @param tipValue, as a String
     * @throws InvalidTipException: if tipValue <= 0
     */
    public static void writeTip(String tipValue) throws InvalidTipException {
        // Tip to be written
        Tip tip = new Tip(tipValue);

        // Find the user in the users collection, then update their tips field
        /* Currently using a different model for the database below, where tips is
         * its own collection. */
         //DocumentReference user = userReference();
        //user.update("tips", FieldValue.arrayUnion(tip.asMap()));

        // Add an entry to the tips collection with UID, value, timestamp
        CollectionReference tips = tipsReference();
        Map<String, Object> data = new HashMap<>();

        data.put("uid", getUID());
        data.put("value", tip.getValue());
        data.put("time", tip.getTime());

        tips.add(data);
    }
    public static void writeTip(Tip tip) throws InvalidTipException {
        CollectionReference tips = tipsReference();
        Map<String, Object> data = new HashMap<>();

        data.put("uid", getUID());
        data.put("value", tip.getValue());
        data.put("time", tip.getTime());

        tips.add(data);
    }

    /** Read tips that the user has input to the database
     */
    public static void loadTips(final ListCallback callback) {
        Query tipsRef = FirebaseFirestore.getInstance()
                .collection("tips")
                .whereEqualTo("uid", getUID())
                .orderBy("time", Query.Direction.ASCENDING);

        tipsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Tip> tipsList = new ArrayList<>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Tip tip = new Tip(document.getData());
                        tipsList.add(tip);
                    }
                    callback.onCallback(tipsList);
                }
            }
        });
    }
    public static void getShifts(final ShiftCallBack callback) {
        Query shiftsRef = shiftsReference()
                .whereEqualTo("uid", getUID())
                .orderBy("start", Query.Direction.DESCENDING);
        final Query tipsRef = FirebaseFirestore.getInstance()
                .collection("tips")
                .whereEqualTo("uid", getUID())
                .orderBy("time", Query.Direction.ASCENDING);

        shiftsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final List<Shift> shiftList = new ArrayList<>(1);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Shift shift = new Shift(document.getData());
                        shiftList.add(shift);

                    }
                    tipsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<Tip> tipsList = new ArrayList<>();
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Tip tip = new Tip(document.getData());
                                    tipsList.add(tip);
                                }
                                callback.onCallback(tipsList,shiftList);
                            }
                        }
                    });
                }
            }
        });

    }

    public static void getMostRecentShift(final ListCallback callback) {
        Query shiftsRef = shiftsReference()
                .whereEqualTo("uid", getUID())
                .orderBy("start", Query.Direction.DESCENDING)
                .limit(1);

        shiftsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    List<Shift> shiftList = new ArrayList<>(1);
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Shift shift = new Shift(document.getData());
                        shiftList.add(shift);
                    }
                    callback.onCallback(shiftList);
                }
            }
        });
    }
    /**
     * @return Database reference for the signed in user's document
     */
    public static DocumentReference userReference() {
        String UID = getUID();

        mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        DocumentReference user = mFirestore
                .collection("users").document(UID);

        return user;
    }

    public static CollectionReference tipsReference() {
        mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        CollectionReference tipsRef = mFirestore
                .collection("tips");

        return tipsRef;
    }

    public static CollectionReference shiftsReference() {
        mFirestore = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        mFirestore.setFirestoreSettings(settings);

        CollectionReference shiftsRef = mFirestore
                .collection("shifts");

        return shiftsRef;
    }


    /**
     * @return FirebaseAuth User ID for the currently signed in user
     */
    public static String getUID() {
        return FirebaseAuth.getInstance().getUid();
    }
}
