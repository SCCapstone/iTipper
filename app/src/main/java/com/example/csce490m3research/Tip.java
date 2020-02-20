/* Object encapsulating a tip entered by a user.
   username: identifier for the user. Should match username used by Firebase authentification.
   time: the date and time that the tip was entered.
   value: the amount in dollars of the tip.

   Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import java.time.ZonedDateTime;

public class Tip {
    private ZonedDateTime time;
    private double value;

    // Constructors

    // Only tip value specified. Use the current user and current time.
    public Tip(double Value) throws InvalidTipException {
        ZonedDateTime Time = ZonedDateTime.now();
        setTime(Time);

        setValue(Value);

    }

    public Tip(ZonedDateTime Time, double Value) throws InvalidTipException {
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

    public String toString() {
        return time.toString() + " : " + value;
    }

}
