package com.example.csce490m3research;

import com.google.firebase.Timestamp;
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
        return "Start: " + getStart().toDate().toString() +
                "\nEnd: " + getEnd().toDate().toString();

    }
}
