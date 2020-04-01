/** Object encapsulating a tip entered by a user.
 *    value: the amount in dollars of the tip.
 *    time: the date and time that the tip was entered.
 *
 * Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Tip {
    double value;
    Timestamp time;
    String uid;

    // Constructors

    public Tip() {

    }

    // Only tip value specified. Use the current user and current time.
    public Tip(String value) throws InvalidTipException {
        Timestamp Time = Timestamp.now();
        setTime(Time);

        setValue(Double.parseDouble(value));
    }
    public Tip(double Value) throws InvalidTipException {
        Timestamp Time = Timestamp.now();
        setTime(Time);

        setValue(Value);
    }

    public Tip(double value, Timestamp time) throws InvalidTipException {
        setValue(value);
        setTime(time);
    }

    // Map constructor:
    // The map should contain keys for value and time.
    public Tip(Map map) {
        value = (double) map.get("value");
        time = (com.google.firebase.Timestamp) map.get("time");
    }


    // Accessors
    public Timestamp getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }

    public String getUid() { return uid; }

    // Setters
    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setValue(double value) throws InvalidTipException {
        if (value <= 0) {
            throw new InvalidTipException("Tip cannot be less than or equal to 0.00 dollars.");
        }

        this.value = value;
    }

    public void setUid(String Uid) { uid = Uid; }

    // Util
    public Map<String, Object> asMap() {
        Map<String, Object> tip = new HashMap<>();

        tip.put("value", value);
        tip.put("time", time);

        return tip;
    }

    // Return a human-readable string for the tip's timestamp
    public String getTimestampString() {
        String pattern = "yyyy/MM/dd hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        return simpleDateFormat.format(time.toDate());
    }

    public String toString() {
        return getTimestampString() + " : " + value;
    }

}
