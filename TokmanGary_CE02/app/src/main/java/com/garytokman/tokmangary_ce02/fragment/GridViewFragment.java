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

import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// GridViewFragment

public class GridViewFragment extends Fragment {

    public final String[] IMAGES = {
            "MgmzpOJ.jpg", "VZmFngH.jpg", "ptE5z9u.jpg",
            "4QKO8Up.jpg", "Vm2UdDH.jpg", "C040ctB.jpg",
            "MScR8za.jpg", "tM1bsAH.jpg", "fS1lKZx.jpg",
            "h8e5rBX.jpg", "KBtUxzq.jpg", "wYXWJZz.jpg",
            "LOUwRC4.jpg", "7ZSQfIu.jpg", "XLJiKqp.jpg",
            "nXVLE9W.jpg", "HYQuj4b.jpg", "R8YIb8d.jpg",
            "cLv3TVc.jpg", "f7pMMdA.jpg", "Dl1aIHV.jpg",
            "UE3ng26.jpg", "1oyYfr0.jpg", "YSJ28fr.jpg",
            "Ey39hl5.jpg", "HAnhjCI.jpg", "En3J4ZF.jpg",
            "wr65Geg.jpg", "7D35kbV.jpg", "Z2WQBPI.jpg"
    };

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
        update();

        return view;
    }

    private void update() {
        mAdapter = new GridAdapter(getActivity(), listOfImageFiles());
        mGridView.setAdapter(mAdapter);
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

    private List<File> listOfImageFiles() {

        List<File> files = new ArrayList<>();

        for (String imageName : IMAGES) {
            files.add(new File(imageName));
        }

        return files;
    }
}
