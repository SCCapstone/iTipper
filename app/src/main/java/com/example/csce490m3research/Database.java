package com.example.csce490m3research;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.LinkedList;
import java.util.List;

/** Static class to handle database operations.
 * Write, read, update.
 */
public class Database {

    /** Write a tip out to the database with the current time and signed in user.
     * @param tipValue
     * @throws InvalidTipException: if tipValue <= 0
     */
    public static void writeTip(double tipValue) throws InvalidTipException {
        DatabaseReference tipsRef = tipsReference();

        // Create a reference to a new tip and set it to the current tip
        Tip tip = new Tip(tipValue);
        tipsRef.push().setValue(tip);
    }

    /** Write a tip out to the database with the current time and signed in user.
     * Argument should be formatted so it can be parsed as a double. i.e.: 7.50, 5.00, 5, 0.42
     * @param tipString
     * @throws InvalidTipException
     */
    public static void writeTip(String tipString) throws InvalidTipException {
        double tipValue = Double.parseDouble(tipString);
        writeTip(tipValue);
    }

    /** Read tips that the user has input to the database and return as an array.
     * TODO: not implemented yet
     */
    public static List<Tip> readTips() {
        List<Tip> tips = new LinkedList<>();
        DatabaseReference tipsRef = tipsReference();

        Query recentTipsQuery = tipsRef.limitToFirst(10);


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
