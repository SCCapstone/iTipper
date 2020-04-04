package com.example.csce490m3research;

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
    private Button button;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View homeFragmentView = inflater.inflate(R.layout.home_fragment, container, false);
        button = homeFragmentView.findViewById(R.id.goToShiftScreen);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterTips(v);
            }
        });
        return  homeFragmentView;
    }

    // Called when user taps "Enter tip" button
    public void enterTips(View view) {
        Intent gotoTips = new Intent(view.getContext(), EnterTipScreenActivity.class);
        startActivity(gotoTips);
    }
}
