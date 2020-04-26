package com.example.csce490m3research;
import android.graphics.Color;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.androidplot.xy.BarFormatter;
import com.androidplot.xy.BarRenderer;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.StepMode;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.nio.channels.SelectionKey;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import java.text.DateFormatSymbols;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import com.androidplot.Region;
import com.androidplot.ui.*;
import com.androidplot.ui.widget.TextLabelWidget;
import com.androidplot.util.*;
import com.androidplot.xy.*;


import static androidx.constraintlayout.widget.Constraints.TAG;
public class  GraphFragment extends Fragment {
    private List<Tip> tips;
    private XYPlot plot;
    private FirebaseFirestore db;

    private MyBarFormatter formatter1;
    private MyBarFormatter formatter2;
    private MyBarFormatter selectionFormatter;
    private TextLabelWidget selectionWidget;
    private Pair<Integer, XYSeries> selection;



    public GraphFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.graph_fragment, container, false);
        getActivity().setTitle("Tips History");
        return v;
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        /*
        Intent intent = new Intent(getActivity(), DisplayTipsGraphActivity.class);
        startActivity(intent);
         */
        final int[] numSeries = {0};
        final int progress;
        final ArrayList<Timestamp> startTimes = new ArrayList<Timestamp>();
        final ArrayList<Timestamp> endTimes = new ArrayList<Timestamp>();
        final ArrayList<String> shiftNames = new ArrayList<String>();
        List<Pair<Timestamp,Timestamp>> StartEndTimes;
        Database.getShifts(new ShiftCallBack() {
            @Override
            public void onCallback(List tipsList, List list) {
                String shiftInfo = "";
                if (!list.isEmpty()) {
                    Shift shift = (Shift) list.get(0);
                    numSeries[0] = list.size();
                    int counter = 0;
                    // iterate through the list
                    for (Object s : list) {
                        // capture value of object toString
                        String temp = s.toString();
                        shiftNames.add(temp);
                        // convert
                        Shift tempShift = (Shift)s;
                        Timestamp start = tempShift.getStart();
                        Timestamp end = tempShift.getEnd();
                        // If the shift's end time is null, a shift is still running
                        if (end == null && start != null) {
                            // set end time to runtime
                            end = Timestamp.now();
                        }
                        startTimes.add(start);
                        endTimes.add(end);
                        //Log.d("List Size: ", Integer.toString(numSeries[0]));
                        Log.d("Shift from my personal method number :  " + counter + " : Named : ", temp);
                        //Log.d("Number of Series: " + numSeries[0] + " : Named : ", temp);
                        Log.d("Time for series Start: " +  start , "   ");
                        Log.d("Time for series End: " +  end , "   ");
                        Log.d("Time series size: " +  startTimes.size() , "   ");
                        counter++;
                    }
                    View v = getView();


                    // get size of tipsList
                    int listSize = tipsList.size();
                    // initialize the arrays
                    // domain is just the tip number but should become timestamp...
                    Number[] rangeLabels = new Number[listSize];
                    // length of start/end times is the amount of shifts
                    int numShifts = startTimes.size();
                    Log.d("list Size: " +   startTimes.size(), "   ");

                    // iterate through list and capture tip values
                    int counter1 = 0;

                    // allow up to 10 shifts of the most recent shifts to be displayed
                    final ArrayList<ArrayList<Number>> aList = new ArrayList<ArrayList<Number>>();
                    ArrayList<Number> list1 = new ArrayList<Number>();
                    ArrayList<Number> list2 = new ArrayList<Number>();
                    ArrayList<Number> list3 = new ArrayList<Number>();
                    ArrayList<Number> list4 = new ArrayList<Number>();
                    ArrayList<Number> list5 = new ArrayList<Number>();
                    ArrayList<Number> list6 = new ArrayList<Number>();
                    ArrayList<Number> list7 = new ArrayList<Number>();
                    ArrayList<Number> list8 = new ArrayList<Number>();
                    ArrayList<Number> list9 = new ArrayList<Number>();
                    ArrayList<Number> list10 = new ArrayList<Number>();

                    for (Object t : tipsList) {
                        // cast to tip
                        Tip tempTip = (Tip) t;
                        // get tip's timestamp
                        Timestamp tipTime = tempTip.getTime();
                        // see where this tip falls in times
                    /*
                    the value 0 if the two Timestamp objects are equal; a value less
                    than 0 if this Timestamp object is before the given argument; and
                    a value greater than 0 if this Timestamp object is after the given
                    argument.
                     */
                        for (int i = 0; i < startTimes.size(); i++) {
                            // add arrayList for each set of times
                            aList.add(new ArrayList<Number>());
                            // see if tip falls between pairs of start and end times
                            if (tipTime.compareTo(endTimes.get(i)) < 0 && tipTime.compareTo(startTimes.get(i)) > 0) {
                                // if tip time is before end and after start then add to shifts arrayList
                                aList.get(i).add(tempTip.getValue());
                                Log.d("list Size" + aList.size(), "   ");
                            }
                        }
                    }

                    if (v == null) {
                        return;
                    }
                    plot = v.findViewById(R.id.plot);
                    // set bounds to the plot
                    plot.setRangeLowerBoundary(0, BoundaryMode.FIXED);
                    plot.setRangeUpperBoundary(32, BoundaryMode.FIXED);
                    // create formatters to use for drawing a series using LineAndPointRenderer
                    // and configure them from xml:
                    LineAndPointFormatter series1Format =
                            new LineAndPointFormatter(Color.RED, Color.GREEN, Color.BLUE, null);
                    BarFormatter bf = new BarFormatter(Color.GREEN, Color.BLACK);
                    BarFormatter bf1 = new BarFormatter(Color.CYAN, Color.BLACK);

                    formatter1 = new MyBarFormatter(Color.GREEN, Color.BLACK);
                    formatter2 = new MyBarFormatter(Color.CYAN, Color.BLACK);


                    //MyBarRenderer renderer = plot.getRenderer(MyBarRenderer.class);
                    // increment each axis by 1
                    plot.setDomainStep(StepMode.INCREMENT_BY_VAL, 1.0);
                    plot.setRangeStep(StepMode.INCREMENT_BY_VAL, 1.0);

                        // create the arrays
                    for (int i = 0; i < startTimes.size(); i++) {
                        if (aList.size() == 0) {
                            break;
                        }
                        // only allow up to seven to be graphed
                        if (i == 0) {
                            list1 = aList.get(i);
                            XYSeries series1 = new SimpleXYSeries(
                                    list1, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(0));
                            plot.addSeries(series1, formatter1);
                            plot.redraw();
                            Log.d("list Size for shift 1" +   list1.size(), "   ");
                        }
                        if (i == 1) {
                            list2 = aList.get(i);
                            XYSeries series2 = new SimpleXYSeries(
                                    list2, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(1));
                            Log.d("list Size for shift 2" +   list2.size(), "   ");
                        }
                        if (i == 2) {
                            list3 = aList.get(i);
                            XYSeries series3 = new SimpleXYSeries(
                                    list3, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(2));
                            Log.d("list Size for shift 3" +   list3.size(), "   ");
                        }
                        if (i == 3) {
                            list4 = aList.get(i);
                            XYSeries series4 = new SimpleXYSeries(
                                    list4, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(3));
                            Log.d("list Size for shift 4" +   list4.size(), "   ");
                        }
                        if (i == 4) {
                            list5 = aList.get(i);
                            XYSeries series5 = new SimpleXYSeries(
                                    list5, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(4));
                            Log.d("list Size for shift 5" +   list5.size(), "   ");
                        }
                        if (i == 5) {
                            list6 = aList.get(i);
                            XYSeries series6 = new SimpleXYSeries(
                                    list6, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(5));
                            Log.d("list Size for shift 6" +   list6.size(), "   ");
                        }
                        if (i == 6) {
                            list7 = aList.get(i);
                            XYSeries series7 = new SimpleXYSeries(
                                    list7, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(6));
                            Log.d("list Size for shift 7" +   list7.size(), "   ");
                        }
                        if (i == 7) {
                            list8 = aList.get(i);
                            XYSeries series8 = new SimpleXYSeries(
                                    list8, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(7));
                            Log.d("list Size for shift 8" +   list8.size(), "   ");
                        }
                        if (i == 8) {
                            list9 = aList.get(i);
                            XYSeries series9 = new SimpleXYSeries(
                                    list9, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(8));
                            Log.d("list Size for shift 9" +   list9.size(), "   ");
                        }
                        if (i == 9) {
                            list10 = aList.get(i);
                            XYSeries series10 = new SimpleXYSeries(
                                    list10, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(9));
                            Log.d("list Size for shift 10" +   list10.size(), "   ");
                        }
                        else
                            break;
                    }

                    // (Y_VALS_ONLY means use the element index as the x value)

                    SeekBar seekBar = (SeekBar) v.findViewById(R.id.seekBar);
                    if (startTimes.size() <= 10)
                        seekBar.setMax(startTimes.size());
                    else
                        seekBar.setMax(10);
                    seekBar.setMin(1);
                    if (seekBar != null) {
                        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                // Write code to perform some action when progress is changed.
                                Log.d("Progress Changed: " ," ");
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                                // Write code to perform some action when touch is started.
                                Log.d("Start Tracking touch: " ," ");
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                                // Write code to perform some action when touch is stopped.
                                Log.d("touch stopped on : " + seekBar.getProgress()," ");
                                plot.clear();
                                ArrayList<Number> listSeekBar = new ArrayList<Number>();
                                if (aList.size() != 0) {
                                    listSeekBar = aList.get(seekBar.getProgress() - 1);
                                }
                                XYSeries series = new SimpleXYSeries(
                                        listSeekBar, SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, shiftNames.get(seekBar.getProgress() - 1));
                                plot.addSeries(series, formatter1);
                                plot.redraw();
                            }
                        });
                    }






                    // add a new series' to the xyplot:
                    //plot.addSeries(series1, bf);
                    //plot.addSeries(series1, formatter1);
                    //plot.addSeries(series2, bf1);


                    //plot.addSeries(series2, formatter2);


                    //BarRenderer barRenderer = (BarRenderer) plot.getRenderer(BarRenderer.class);
                    MyBarRenderer renderer = ((MyBarRenderer) plot.getRenderer(MyBarRenderer.class));
                    if (renderer != null) {
                        renderer.setBarOrientation(BarRenderer.BarOrientation.SIDE_BY_SIDE);
                        renderer.setBarGroupWidth(BarRenderer.BarGroupWidthMode.FIXED_GAP,0.1f);
                    }

                    plot.redraw();
                }
            }
        });
    }


    class MyBarFormatter extends BarFormatter {
        public MyBarFormatter(int fillColor, int borderColor) {
            super(fillColor, borderColor);
        }
        @Override
        public Class<? extends SeriesRenderer> getRendererClass() {
            return MyBarRenderer.class;
        }
        @Override
        public SeriesRenderer doGetRendererInstance(XYPlot plot) {
            return new MyBarRenderer(plot);
        }
    }
    class MyBarRenderer extends BarRenderer<MyBarFormatter> {
        public MyBarRenderer(XYPlot plot) {
            super(plot);
        }
        /**
         * Implementing this method to allow us to inject our
         * special selection getFormatter.
         * @param index index of the point being rendered.
         * @param series XYSeries to which the point being rendered belongs.
         * @return
         */
        @Override
        public MyBarFormatter getFormatter(int index, XYSeries series) {
            if (selection != null &&
                    selection.second == series &&
                    selection.first == index) {
                return selectionFormatter;
            } else {
                return getFormatter(series);
            }
        }
    }
}
