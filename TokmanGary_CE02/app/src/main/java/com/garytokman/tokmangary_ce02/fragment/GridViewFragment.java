package com.garytokman.tokmangary_ce02.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.adapter.GridAdapter;

import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// GridViewFragment

public class GridViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.grid_layout, container, false);

        GridView gridView = (GridView) view.findViewById(R.id.gridView);

        List<String> strings = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strings.add("Gary");
        }

        GridAdapter adapter = new GridAdapter(getActivity(), strings);
        gridView.setAdapter(adapter);

        return view;
    }
}
