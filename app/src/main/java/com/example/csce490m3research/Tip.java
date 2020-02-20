/* Object encapsulating a tip entered by a user.
   username: identifier for the user. Should match username used by Firebase authentification.
   time: the date and time that the tip was entered.
   value: the amount in dollars of the tip.

   Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import java.math.BigDecimal;
import java.time.ZonedDateTime;

public class Tip {
    private String time;
    private String value;

    // Constructors

    // Only tip value specified. Use the current user and current time.
    public Tip(String Value) throws InvalidTipException {
        ZonedDateTime Time = ZonedDateTime.now();
        setTime(Time.toString());

        setValue(Value);

    }

    public Tip(String Time, String Value) throws InvalidTipException {
        setTime(Time);

        setValue(Value);
    }

    public String getTime() {
        return time;
    }

    public String getValue() {
        return value;
    }

    // Setters
    public void setTime(String Time) {
        time = Time;
    }

    public void setValue(String Value) throws InvalidTipException {
        BigDecimal val = new BigDecimal(Value);
        if (val.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTipException("Tip cannot be less than or equal to 0.00 dollars.");
        }
        value = Value;
    }

    public String toString() {
        return time + " : " + value;
    }

}
