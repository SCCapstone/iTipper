package com.example.csce490m3research;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class ShiftAdapter extends FirestoreRecyclerAdapter<Shift, ShiftAdapter.ShiftHolder> {

    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ShiftAdapter(@NonNull FirestoreRecyclerOptions<Shift> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ShiftHolder holder, int position, @NonNull Shift model) {
        holder.shiftTextView.setText(model.toString());
        holder.shift = model;
    }

    @NonNull
    @Override
    public ShiftHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shift_item,
                parent, false);
        return new ShiftHolder(v);
    }

    class ShiftHolder extends RecyclerView.ViewHolder {
        TextView shiftTextView;
        Shift shift;

        public ShiftHolder(@NonNull View itemView) {
            super(itemView);
            shiftTextView = itemView.findViewById(R.id.shift_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    // When the shift view is clicked:
                    Context context = v.getContext();

                    Intent gotoTips = new Intent(context, TipsListActivity.class);

                    gotoTips.putExtra("startSeconds", shift.getStart().getSeconds());
                    gotoTips.putExtra("startNanos", shift.getStart().getNanoseconds());

                    gotoTips.putExtra("endSeconds", shift.getEnd().getSeconds());
                    gotoTips.putExtra("endNanos", shift.getEnd().getNanoseconds());

                    context.startActivity(gotoTips);
                }
            });
        }
    }
}
