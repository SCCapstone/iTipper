/* Object encapsulating a tip entered by a user.
   username: identifier for the user. Should match username used by Firebase authentification.
   time: the date and time that the tip was entered.
   value: the amount in dollars of the tip.

   Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.ZonedDateTime;

public class Tip {
    private ZonedDateTime time;
    private double value;

    // Constructors

    // Only tip value specified. Use the current user and current time.
    public Tip(double Value)
            throws InvalidTipException {

        ZonedDateTime Time = ZonedDateTime.now();
        setTime(Time);

        setValue(Value);

    }

    // Constructor if the time isn't specified. Use the current time.
    public Tip(String UserID, double Value)
            throws InvalidTipException {

        ZonedDateTime Time = ZonedDateTime.now();
        setTime(Time);

        setValue(Value);
    }

    public Tip(String UserID, ZonedDateTime Time, double Value)
            throws InvalidTipException {

        setTime(Time);

        setValue(Value);
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }

    // Setters
    public void setTime(ZonedDateTime Time) {
        time = Time;
    }

    public void setValue(double Value) throws InvalidTipException {
        if (Value <= 0.00) {
            throw new InvalidTipException("Tip cannot be less than or equal to 0.00 dollars.");
        }
        value = Value;
    }

    // Write tip out to database
    public void write() {
        String userID = FirebaseAuth.getInstance().getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference tipsRef =
                database.getReference("users").child(userID).child("tips");

        // Create a reference to a new tip and set it to the current tip
        tipsRef.push().setValue(this);
    }

    // Write a tip out to database. No need to instantiate an object, just give the tip value
    // as an argument.
    public static void writeTipString(String tipString)
            throws InvalidTipException {

        double tipValue = Double.parseDouble(tipString);
        Tip t = new Tip(tipValue);

        t.write();
    }

}
