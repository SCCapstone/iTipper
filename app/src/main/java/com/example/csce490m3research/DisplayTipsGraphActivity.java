package com.example.csce490m3research;
import android.app.Activity;
//import android.graphics.Color;
//import android.graphics.DashPathEffect;
import android.graphics.*;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import java.util.*;
import androidx.annotation.NonNull;
import com.androidplot.util.PixelUtils;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.FastXYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.RectRegion;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYGraphWidget;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.*;
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
import android.app.Fragment;

import static androidx.constraintlayout.widget.Constraints.TAG;


/**
 * Class displays the tip graph activity
 */
public class DisplayTipsGraphActivity extends Activity {

    private List<Tip> tips;

    private XYPlot plot;

    private FirebaseFirestore db;

    //private Number[] domainLabels;

    //private Number[] rangeLabels;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Database.loadTips(new ListCallback() {
            @Override
            public void onCallback(List tipsList) {

                // New Code
                // get size of tipsList
                int listSize = tipsList.size();
                // initialize the arrays
                // domain is just the tip number but should become timestamp...
                final Number[] domainLabels = new Number[listSize];
                Number[] rangeLabels = new Number[listSize];
                // populate with values

                for (int i = 0; i < listSize; i++) {
                    domainLabels[i] = i + 1;
                }
                // iterate through list and capture tip values
                int counter = 0;
                for (Object t : tipsList) {
                    // capture value of object toString
                    String temp = t.toString();
                    Log.d("String: ", temp);
                    Log.d("Value " + counter +": ", temp);
                    // get index of " : " just before tip
                    int index = temp.indexOf(" : ");
                    // shorten temp to just tip value
                    temp = temp.substring(index + 4);
                    double tipVal = Double.parseDouble(temp);
                    // add double to range Array
                    rangeLabels[counter] = tipVal;
                    // increment counter
                    counter++;
                }
                setContentView(R.layout.activity_display_graph);
                plot = findViewById(R.id.plot);
                // set bounds to the plot
                plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
                plot.setRangeUpperBoundary(30,BoundaryMode.FIXED);

                // create a couple arrays of y-values to plot:
                // (Y_VALS_ONLY means use the element index as the x value)
                XYSeries series1 = new SimpleXYSeries(
                        Arrays.asList(rangeLabels), SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, "Tips");
                // create formatters to use for drawing a series using LineAndPointRenderer
                // and configure them from xml:
                LineAndPointFormatter series1Format =
                        new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
                BarFormatter bf = new BarFormatter(Color.GREEN, Color.BLACK);

                //bf.setMarginLeft(PixelUtils.dpToPix(1));
                //bf.setMarginRight(PixelUtils.dpToPix(10));

                BarRenderer renderer = plot.getRenderer(BarRenderer.class);
                renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_WIDTH, PixelUtils.dpToPix(25));

                // increment each axis by 1
                plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0);
                plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0);
                // just for fun, add some smoothing to the lines:
                //series1Format.setInterpolationParams(
                       // new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
                // add a new series' to the xyplot:
                //plot.addSeries(series1, series1Format);
                plot.addSeries(series1, bf);
            }
        });
    }


}
