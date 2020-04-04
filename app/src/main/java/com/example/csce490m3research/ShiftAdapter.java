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

public class ShiftAdapter extends FirestoreRecyclerAdapter<Shift, ShiftAdapter.ShiftHolder> {
    private OnItemClickListener listener;

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

    public void deleteItem(int position) {
        getSnapshots().getSnapshot(position).getReference().delete();
    }

    class ShiftHolder extends RecyclerView.ViewHolder {
        TextView shiftTextView;
        Button editShiftButton;
        Shift shift;

        public ShiftHolder(@NonNull View itemView) {
            super(itemView);
            shiftTextView = itemView.findViewById(R.id.shift_text_view);
            editShiftButton = itemView.findViewById(R.id.edit_shift_button);

            // Add a listener for when the card holding the shift data is clicked
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /* When the shift view is clicked, create an intent to start a
                       a new activity to view a list of tips, and pass on the data
                       for the shift that was clicked on so the new activity can query
                       for tips recorded during that shift.
                    */
                    Context context = v.getContext();

                    Intent gotoTips = new Intent(context, TipsListActivity.class);

                    String path = getSnapshots()
                            .getSnapshot(getAdapterPosition())
                            .getReference()
                            .getPath();
                    gotoTips.putExtra("path", path);

                    gotoTips.putExtra("startSeconds", shift.getStart().getSeconds());
                    gotoTips.putExtra("startNanos", shift.getStart().getNanoseconds());

                    gotoTips.putExtra("endSeconds", shift.getEnd().getSeconds());
                    gotoTips.putExtra("endNanos", shift.getEnd().getNanoseconds());

                    context.startActivity(gotoTips);
                }
            });

            editShiftButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Context context = v.getContext();

                    Intent editShift = new Intent(context, EditShiftActivity.class);

                    DocumentSnapshot ds = getSnapshots().getSnapshot(getAdapterPosition());
                    String path = ds.getReference().getPath();
                    editShift.putExtra("path", path);

                    Timestamp start = (Timestamp) ds.get("start");
                    Date startDate = start.toDate();
                    editShift.putExtra("start", startDate.toString());

                    if (ds.contains("end")) {
                        Timestamp end = (Timestamp) ds.get("end");
                        Date endDate = end.toDate();
                        editShift.putExtra("end", endDate.toString());
                    }

                    context.startActivity(editShift);
                }
            });
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
