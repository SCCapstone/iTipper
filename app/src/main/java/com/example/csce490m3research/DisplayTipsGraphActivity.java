package com.example.csce490m3research;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;

import androidx.annotation.NonNull;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.FastXYSeries;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.RectRegion;
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

        Database.loadTips(new Callback() {
            @Override
            public void onCallback(List<Tip> tipsList) {
                plot = findViewById(R.id.plot);
            }
        });
    }


}
