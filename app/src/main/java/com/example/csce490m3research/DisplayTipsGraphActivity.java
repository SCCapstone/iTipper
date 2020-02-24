package com.example.csce490m3research;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class DisplayTipsGraphActivity extends Activity {

    private List<Tip> tips;

    private XYPlot plot;

    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_graph);

        // initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.plot);

        getData();
    }

    void getData() {
        Database d = new Database();

        d.loadTips(new Callback() {
            @Override
            public void onCallback(List<Tip> data) {
                System.out.println("Callback data: " + data);
                tips = new ArrayList<>(data);

                // TODO: use tip data, user better domain labels
                final Number[] domainLabels = new Number[tips.size()];
                Number[] series1Numbers = new Number[tips.size()];

                // Load the actual values for each tip into the series
                // Also load domain labels. See todo above
                for (int i = 0; i < tips.size(); i++) {
                    domainLabels[i] = i;
                    series1Numbers[i] = tips.get(i).getValue();
                }

                // turn the above arrays into XYSeries':
                // (Y_VALS_ONLY means use the element index as the x value)
                XYSeries series1 = new SimpleXYSeries(
                        Arrays.asList(series1Numbers), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Series1");

                // create formatters to use for drawing a series using LineAndPointRenderer
                // and configure them from xml:
                LineAndPointFormatter series1Format =
                        new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);

                plot.addSeries(series1, series1Format);

                plot.getGraph().getLineLabelStyle(XYGraphWidget.Edge.BOTTOM).setFormat(new Format() {
                    @Override
                    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
                        int i = Math.round(((Number) obj).floatValue());
                        return toAppendTo.append(domainLabels[i]);
                    }
                    @Override
                    public Object parseObject(String source, ParsePosition pos) {
                        return null;
                    }
                });
            }
        });
    }

}
