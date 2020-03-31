package com.example.csce490m3research;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class TipsListActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference tipsRef = db.collection("tips");

    Timestamp start, end;

    private TipAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips_list);
        setTitle("Tips (All)");

        // Check if the intent was passed shift data, and if so extract it.
        Intent intent = getIntent();
        if (intent.hasExtra("startSeconds") && intent.hasExtra("startNanos")) {
            long startSeconds = intent.getLongExtra("startSeconds", 0);
            int startNanos = intent.getIntExtra("startNanos", 0);

            start = new Timestamp(startSeconds, startNanos);

            Shift shift = new Shift();
            shift.setStart(start);

            if (intent.hasExtra("endSeconds") && intent.hasExtra("endNanos")) {
                long endSeconds = intent.getLongExtra("endSeconds", 0);
                int endNanos = intent.getIntExtra("endNanos", 0);

                end = new Timestamp(endSeconds, endNanos);
                shift.setEnd(end);
            }
            // Display information for the shift at the start of the screen, to help the user.
            setTitle("Tips: " + shift.toString());
        }

        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        // Query the tips collection for all tips from the current user.
        Query query = tipsRef.whereEqualTo("uid", Database.getUID())
                .orderBy("time", Query.Direction.DESCENDING);

        // If the intent was passed shift data, only display tips entered during that shift.
        if (start != null) {
            query = query.whereGreaterThanOrEqualTo("time", start);
        }
        if (end != null) {
            query = query.whereLessThanOrEqualTo("time", end);
        }

        FirestoreRecyclerOptions<Tip> options = new FirestoreRecyclerOptions.Builder<Tip>()
                .setQuery(query, Tip.class)
                .build();

        adapter = new TipAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.tipRecyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
