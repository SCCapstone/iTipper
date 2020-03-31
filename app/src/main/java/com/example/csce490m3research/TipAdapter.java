package com.example.csce490m3research;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TipAdapter extends FirestoreRecyclerAdapter<Tip, TipAdapter.TipHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public TipAdapter(@NonNull FirestoreRecyclerOptions<Tip> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull TipAdapter.TipHolder holder, int position, @NonNull Tip model) {
        // Convert number value to string
        String tipValue = String.valueOf(model.getValue());
        // Set "Tip Value" field in the card to show the value
        holder.tipValueView.setText("$" + tipValue);

        // Set "Timestamp" field in the card to show the timestamp for the tip
        holder.tipTimestampView.setText(model.getTimestampString());
    }

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    @NonNull
    @Override
    public TipAdapter.TipHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tip_item,
                parent, false);
        return new TipAdapter.TipHolder(v);
    }

    class TipHolder extends RecyclerView.ViewHolder {
        TextView tipValueView;
        TextView tipTimestampView;

        public TipHolder(@NonNull View itemView) {
            super(itemView);
            tipValueView = itemView.findViewById(R.id.tip_value_view);
            tipTimestampView = itemView.findViewById(R.id.tip_timestamp_view);
        }
    }
}
