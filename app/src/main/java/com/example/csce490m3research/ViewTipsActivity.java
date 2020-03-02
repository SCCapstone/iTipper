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


        Database.loadTips(new ListCallback() {
            @Override
            public void onCallback(List tipList) {
                databaseTips = (TextView) findViewById(R.id.tipsView);

                String stringToWrite = "";
                for (Object t : tipList) {
                    // capture value of object toString
                    String temp = t.toString();
                    // get index of " : " just before tip
                    int index = temp.indexOf(" : ");
                    // shorten temp to just tip value
                    temp = temp.substring(index + 3);
                    double tipVal = Double.parseDouble(temp);
                    stringToWrite += t.toString() + "\n";
                    //stringToWrite += tipVal + "\n";
                }

                databaseTips.setText(stringToWrite);
            }
        });
    }
}
