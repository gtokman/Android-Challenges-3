package com.garytokman.tokmangary_ce02.fragment;

import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
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

    public static final String ACTION_IMAGE_DOWNLOAD = "ACTION_IMAGE_DOWNLOAD";
    private static final String TAG = GridViewFragment.class.getSimpleName();
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: " + intent.getAction());
            mAdapter.notifyDataSetChanged();
        }
    };
    private GridAdapter mAdapter;
    private GridView mGridView;

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

        mGridView = (GridView) view.findViewById(R.id.gridView);
        mAdapter = new GridAdapter(getActivity());
        mGridView.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(ACTION_IMAGE_DOWNLOAD);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mReceiver);
    }
}
