package com.garytokman.tokmangary_ce02.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.service.ImageService;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

// Gary Tokman
// MDF3 - 1610
// GridAdapter

public class GridAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mImageNames = new ArrayList<>(Arrays.asList(ImageService.IMAGES));


    public GridAdapter(Context context) {
        mContext = context;
    }

    @Override
    public int getCount() {
        return mImageNames.size();
    }

    @Override
    public Object getItem(int i) {
        return mImageNames.get(i);
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
        viewHolder.bindView(mImageNames.get(i), mContext);

        return view;
    }

    private static class ViewHolder implements View.OnClickListener {

        ImageView mImageView;
        Context mContext;
        String imageName;

        ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.gridImage);
            mImageView.setOnClickListener(this);
        }

        void bindView(String imageName, Context context) {
            mImageView.setImageBitmap(loadImageData(imageName, context));
            mContext = context;
            this.imageName = imageName;
        }

        private Bitmap loadImageData(String image, Context context) {
            try {
                // Get file
                FileInputStream fileInputStream = context.openFileInput(image);

                byte[] imageBytes = IOUtils.toByteArray(fileInputStream);
                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inSampleSize = 5;

                Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length, options);
//                // Convert to bitmap
//                Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);

                return bitmap;
            } catch (IOException e) {
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
            File file = new File(mContext.getFilesDir(), imageName);
            Log.d(TAG, "onClick: " + file.getAbsolutePath());
            intent.setDataAndType(Uri.parse(file.getAbsolutePath()), "image/jpg");
            view.getContext().startActivity(intent);
        }

    }

}
