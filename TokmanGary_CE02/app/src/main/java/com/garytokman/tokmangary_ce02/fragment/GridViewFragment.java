package com.garytokman.tokmangary_ce02.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.adapter.GridAdapter;
import com.garytokman.tokmangary_ce02.service.ImageService;

// Gary Tokman
// MDF3 - 1610
// GridViewFragment

public class GridViewFragment extends Fragment {

    private GridAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Start service
        Intent serviceIntent = new Intent(getActivity(), ImageService.class);
        getActivity().startService(serviceIntent);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate view
        View view = inflater.inflate(R.layout.grid_layout, container, false);

        // Init
        GridView gridView = (GridView) view.findViewById(R.id.gridView);
        mAdapter = new GridAdapter(getActivity());
        gridView.setAdapter(mAdapter);

        return view;
    }

    public void updateGridView() {
        mAdapter.notifyDataSetChanged();
    }
}
