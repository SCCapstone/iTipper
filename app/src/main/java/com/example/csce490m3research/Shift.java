package com.example.csce490m3research;

import com.google.firebase.Timestamp;

import java.text.SimpleDateFormat;
import java.util.Map;

public class Shift {
    Timestamp start, end;

    public Shift() {

    }

    public Shift(Map map) {
        start = (Timestamp) map.get("start");
        end = (Timestamp) map.get("end");
    }

    public Timestamp getStart() {
        return start;
    }
    public Timestamp getEnd() {
        return end;
    }

    public void setStart(Timestamp Start) {
        start = Start;
    }
    public void setEnd(Timestamp End) {
        end = End;
    }

    public String toString() {
        String startPattern = "yyyy/MM/dd hh:mm a";
        SimpleDateFormat startFormat = new SimpleDateFormat(startPattern);

        String endPattern = "hh:mm a";
        SimpleDateFormat endFormat = new SimpleDateFormat(endPattern);

        String ret = startFormat.format(start.toDate());

        if (end != null) {
            ret += " - " + endFormat.format(end.toDate());
        } else {
            // Inform the user that the shift is still being recorded
            ret += " (Ongoing)";
        }

        return ret;
    }
}
