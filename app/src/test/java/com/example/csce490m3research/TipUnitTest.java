package com.example.csce490m3research;

import com.google.firebase.Timestamp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class TipUnitTest {

    @Test
    public void tipCreatedCorrectly() {
        Timestamp testTime = Timestamp.now();
        double testTipValue = 5.00;

        Tip testTip;

        try {
            testTip = new Tip();
            testTip.setValue(testTipValue);
            testTip.setTime(testTime);
            assertEquals(testTip.getTime(), testTime);
            assertEquals(testTip.getValue(), testTipValue, 0.0);
        } catch (InvalidTipException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnNegativeTip() throws InvalidTipException {
        Tip testTip = new Tip("-5");
    }

    @Test(expected = InvalidTipException.class)
    public void throwsExceptionOnZeroTip() throws InvalidTipException {
        Tip testTip = new Tip("0.00");
    }

    @Test
    public void mapTest() {
        Map<String, Object> map = new HashMap<>();

        double value = 5.00;
        map.put("value", value);

        Timestamp now = Timestamp.now();
        map.put("time", now);

        String testUser = "test_user";
        map.put("uid", testUser);

        Tip tip = new Tip(map);
        assertEquals(tip.getValue(), value, 0.0);

        assertEquals(tip.getTime(), now);

        assertEquals(tip.getUid(), testUser);

        Map<String, Object> newMap = tip.asMap();
        assertTrue(newMap.containsKey("value"));
        assertTrue(newMap.containsKey("time"));
        assertTrue(newMap.containsKey("uid"));
    }

    @Test
    public void timestampFormat() throws InvalidTipException {
        String pattern = "yyyy/MM/dd hh:mm a";
        SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.getDefault());
        Timestamp now = Timestamp.now();
        String nowFormatted = format.format(now.toDate());

        Tip tip = new Tip();
        tip.setTime(now);

        String formattedTimestamp = tip.getTimestampString();

        assertEquals(nowFormatted, formattedTimestamp);

        double value = 5.00;
        tip.setValue(value);
        assertEquals(tip.toString(), nowFormatted + " : $" + value);

    }
}
