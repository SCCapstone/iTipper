package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChooseGraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_graph);
    }

    public void graphDisplay(View view) {
        Intent gotoGraphExample = new Intent(this, DisplayGraphActivity.class);
        startActivity(gotoGraphExample);
    }

    public void graphTipsDisplay(View view) {
        Intent gotoTipsGraph = new Intent(this, DisplayTipsGraphActivity.class);
        startActivity(gotoTipsGraph);
    }
}