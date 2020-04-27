package com.example.csce490m3research;

import com.google.firebase.Timestamp;

import org.junit.Test;
import org.mockito.internal.verification.Times;

import java.sql.Time;
import java.util.HashMap;
import java.util.Map;

public class ShiftUnitTest {

    @Test
    public void stringIdentifiesOngoingShift() {
        Shift shift = new Shift();
        shift.setStart(Timestamp.now());

        assert shift.toString().contains("Ongoing");
    }

    @Test
    public void stringNotOngoingProperly() {
        Shift shift = new Shift();
        shift.setStart(Timestamp.now());
        shift.setEnd(Timestamp.now());

        assert !shift.toString().contains("Ongoing");
    }

    @Test
    public void testMap() {
        Map map = new HashMap<>();

        Timestamp now = Timestamp.now();
        map.put("start", now);

        Timestamp now2 = Timestamp.now();
        map.put("end", now2);

        Shift shift = new Shift(map);
        assert shift.getStart().equals(now);
        assert shift.getEnd().equals(now2);
    }

    @Test
    public void uid() {
        Shift shift = new Shift();
        String expected = "test_user";
        shift.setUid(expected);

        assert shift.getUid().equals(expected);
    }
}
