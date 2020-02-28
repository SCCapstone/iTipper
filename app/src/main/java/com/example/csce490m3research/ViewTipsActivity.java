package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class ViewTipsActivity extends AppCompatActivity {

    TextView databaseTips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_tips);


        Database.loadTips(new Callback() {
            @Override
            public void onCallback(List<Tip> tipList) {
                databaseTips = (TextView) findViewById(R.id.tipsView);

                String stringToWrite = "";
                for (Tip t : tipList) {
                    stringToWrite += t.toString() + "\n";
                }

                databaseTips.setText(stringToWrite);
            }
        });
    }
}
