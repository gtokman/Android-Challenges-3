package com.garytokman.tokmangary_ce02.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.thread.BitmapThread;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

// Gary Tokman
// MDF3 - 1610
// GridAdapter

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<File> mImages = new ArrayList<>();

    public GridAdapter(Context context, List<File> images) {
        mContext = context;
        mImages = images;
    }

    @Override
    public int getCount() {
        return mImages.size();
    }

    @Override
    public Object getItem(int i) {
        return mImages.get(i);
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
        viewHolder.bindView(mImages.get(i), mContext);

        return view;
    }

    private static class ViewHolder implements View.OnClickListener, BitmapThread.UpdateUI {

        ImageView mImageView;

        ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.gridImage);
            mImageView.setOnClickListener(this);
        }

        void bindView(File file, Context context) {
            mImageView.setImageBitmap(loadImageData(file.toString(), context));
//            BitmapThread bitMap = new BitmapThread(file.toString(), context);
//            bitMap.setName("BitmapThread");
//            bitMap.start();

        }

        private Bitmap loadImageData(String image, Context context) {
            try {
                FileInputStream fileInputStream = context.openFileInput(image);
                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
                Bitmap imageBitMap = Bitmap.createScaledBitmap(bitmap, 75, 75, true);

                return imageBitMap;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        public void onClick(View view) {
            Log.d(GridAdapter.class.getSimpleName(), "onClick: ");
            // TODO: pass in image
            // Start intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.setDataAndType(view.get, "image/png");
//            view.getContext().startActivity(Intent.createChooser(intent, "View image"));
        }

        @Override
        public void getImage(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
        }
    }

}
