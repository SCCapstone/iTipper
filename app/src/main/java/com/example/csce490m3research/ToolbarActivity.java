package com.example.csce490m3research;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import java.util.Objects;

/**
 * Base activity that other activities can extend if they want to include a toolbar.
 * When extending this class, make sure you call setContentView BEFORE super.onCreate.
 * Otherwise, the code here will have no reference to the toolbar.
 */
public class ToolbarActivity extends AppCompatActivity {

    Toolbar toolbar;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Define actions to perform when an item on the screen's action bar is selected by the user.
     * @param item The element on the action bar that the user selected
     * @return true if successful, false otherwise
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setTitle(String title) {
        if (actionBar != null) {
            actionBar.setTitle(title);
        }
    }
}
