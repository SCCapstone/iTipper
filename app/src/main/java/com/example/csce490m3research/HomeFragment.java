package com.example.csce490m3research;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* Add a listener to the "View your activity" button to go to
         * ShiftLiftActivity when clicked. */
        Button viewUserActivityButton = (Button) getView().findViewById(R.id.view_activity_button);
        viewUserActivityButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ShiftListActivity.class));
            }
        });

        /* Add a listener to the "View all your tips" button to go to
         * TipsListActivity when clicked. */
        Button viewAllTipsButton = (Button) getView().findViewById(R.id.view_all_tips_button);
        viewAllTipsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TipsListActivity.class));
            }
        });
    }
}
