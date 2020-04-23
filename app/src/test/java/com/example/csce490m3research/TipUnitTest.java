package com.example.csce490m3research;

import com.google.firebase.Timestamp;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class TipUnitTest {

    @Test
    public void tipCreatedCorrectly() {
        Timestamp testTime = Timestamp.now();
        double testTipValue = 5.00;

        Tip testTip;

        try {
            testTip = new Tip(testTipValue);
            testTip.setTime(testTime);
            assertTrue(testTip.getTime().equals(testTime));
            assertTrue(testTip.getValue() == testTipValue);
        } catch (InvalidTipException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnNegativeTip() throws InvalidTipException, NullUsernameException {
        Tip testTip = new Tip("-5");
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnZeroTip() throws InvalidTipException, NullUsernameException {
        Tip testTip = new Tip("0.00");
    }

}
