/* Object encapsulating a tip entered by a user.
   username: identifier for the user. Should match username used by Firebase authentification.
   time: the date and time that the tip was entered.
   value: the amount in dollars of the tip.

   Authors: Tyler Chambers
 */

package com.example.csce490m3research;

import java.time.ZonedDateTime;

public class Tip {
    private String username;
    private ZonedDateTime time;
    private double value;

    // Constructors

    // Constructor if the time isn't specified. Use the current time.
    public Tip(String Username, double Value)
            throws NullUsernameException, InvalidTipException {

        setUsername(Username);

        ZonedDateTime Time = ZonedDateTime.now();
        setTime(Time);

        setValue(Value);
    }

    public Tip(String Username, ZonedDateTime Time, double Value)
            throws NullUsernameException, InvalidTipException {

        setUsername(Username);

        setTime(Time);

        setValue(Value);
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public ZonedDateTime getTime() {
        return time;
    }

    public double getValue() {
        return value;
    }

    // Setters
    public void setUsername(String Username) throws NullUsernameException {
        if (Username.isEmpty()) {
            throw new NullUsernameException("Username is null or empty.");
        }
        username = Username;
    }

    public void setTime(ZonedDateTime Time) {
        time = Time;
    }

    public void setValue(double Value) throws InvalidTipException {
        if (Value <= 0.00) {
            throw new InvalidTipException("Tip cannot be less than or equal to 0.00 dollars.");
        }
        value = Value;
    }

}
