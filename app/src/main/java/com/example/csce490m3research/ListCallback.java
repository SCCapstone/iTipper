package com.example.csce490m3research;

import java.util.List;

/**
 * Interface for to callback a list
 * Used in shift dashboard, view tip history, and tip history graph
 */
public interface ListCallback {
    void onCallback(List list);
}
