package com.example.csce490m3research;

import org.junit.Test;

import java.time.ZonedDateTime;

import static org.junit.Assert.*;

public class TipUnitTest {

    @Test
    public void tipCreatedCorrectly() {
        String testUsername = "TESTUSER";
        ZonedDateTime testTime = ZonedDateTime.now();
        double testTipValue = 5.00;

        Tip testTip;

        try {
            testTip = new Tip(testUsername, testTime, testTipValue);
            assertTrue(testTip.getUsername().equals(testUsername));
            assertTrue(testTip.getTime().equals(testTime));
            assertTrue(testTip.getValue() == testTipValue);
        } catch (NullUsernameException | InvalidTipException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = NullUsernameException.class)
    public void throwsExceptionOnEmptyUsername() throws InvalidTipException, NullUsernameException {
        Tip testTip = new Tip("", 5.00);
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnNegativeTip() throws InvalidTipException, NullUsernameException {
        Tip testTip = new Tip("TESTUSER", -5.00);
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnZeroTip() throws InvalidTipException, NullUsernameException {
        Tip testTip = new Tip("TESTUSER", 0.00);
    }

}
