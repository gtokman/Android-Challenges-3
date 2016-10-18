package com.garytokman.tokmangary_ce07.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.garytokman.tokmangary_ce07.R;

// Gary Tokman
// MDF3 - 1610
// DetailsFragment

public class DetailsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detail_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        TextView makeLabel = (TextView) view.findViewById(R.id.makeTextView);
        TextView modelLabel = (TextView) view.findViewById(R.id.modelTextView);
        TextView yearLabel = (TextView) view.findViewById(R.id.yearTextView);
        String text = yearLabel.getText().toString();
    }
}
