package com.garytokman.tokmangary_ce02.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.garytokman.tokmangary_ce02.R;

import java.util.List;

// Gary Tokman
// MDF3 - 1610
// GridAdapter

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mStringList;

    public GridAdapter(Context context, List<String> stringList) {
        mContext = context;
        mStringList = stringList;
    }

    @Override
    public int getCount() {
        return mStringList.size();
    }

    @Override
    public Object getItem(int i) {
        return mStringList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;

        if (view == null) {
            // Inflate view
            view = LayoutInflater.from(mContext).inflate(R.layout.grid_item, viewGroup, false);

            // Init view holder
            viewHolder = new ViewHolder(view);

            // Set tag
            view.setTag(viewHolder);
        } else {
            // View exists get it
            viewHolder = (ViewHolder) view.getTag();
        }

        // Update UI
        viewHolder.bindView();

        return view;
    }

    private static class ViewHolder {

        ImageView mImageView;

        public ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.gridImage);
        }

        public void bindView() {
            mImageView.setImageResource(R.mipmap.ic_launcher);
        }
    }

}
