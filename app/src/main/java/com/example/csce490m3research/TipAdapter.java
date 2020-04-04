package com.example.csce490m3research;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;

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
        Button editTipButton;

        public TipHolder(@NonNull View itemView) {
            super(itemView);
            tipValueView = itemView.findViewById(R.id.tip_value_view);
            tipTimestampView = itemView.findViewById(R.id.tip_timestamp_view);
            editTipButton = itemView.findViewById(R.id.edit_tip_button);

            // Add a listener for when the edit button on the card is clicked
            editTipButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent editTip = new Intent(context, EditTipActivity.class);

                    DocumentSnapshot ds = getSnapshots().getSnapshot(getAdapterPosition());
                    String path = ds.getReference().getPath();
                    editTip.putExtra("path", path);

                    String tipValue = ds.get("value").toString();
                    editTip.putExtra("value", tipValue);

                    Timestamp timestamp = (Timestamp) ds.get("time");
                    Date date = timestamp.toDate();
                    editTip.putExtra("date", date.toString());

                    context.startActivity(editTip);
                }
            });
        }
    }
}
