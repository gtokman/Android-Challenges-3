package com.garytokman.tokmangary_ce02.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.garytokman.tokmangary_ce02.R;
import com.garytokman.tokmangary_ce02.service.ImageService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.content.ContentValues.TAG;

// Gary Tokman
// MDF3 - 1610
// GridAdapter

public class GridAdapter extends BaseAdapter {

    private final Context mContext;
    private final List<String> mImageNames = new ArrayList<>(Arrays.asList(ImageService.IMAGES));


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

        final ImageView mImageView;
        Context mContext;
        String mImageName;

        ViewHolder(View view) {
            mImageView = (ImageView) view.findViewById(R.id.gridImage);
            mImageView.setOnClickListener(this);
        }

        void bindView(String imageName, Context context) {
//            mImageView.setImageBitmap(loadImageData(imageName, context));
            mContext = context;
            this.mImageName = imageName;
            ProcessImage processImage = new ProcessImage();
            processImage.execute();


        }

        private Bitmap loadImageData(String image, Context context) {
            // Get file stream
            File file = new File(context.getFilesDir(), image);

            // Create options
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 6;

            return BitmapFactory.decodeFile(file.getPath(), options);
        }

        class ProcessImage extends AsyncTask<Void, Void, Bitmap> {

            @Override
            protected Bitmap doInBackground(Void... voids) {
                return loadImageData(mImageName, mContext);
            }

            private Bitmap loadImageData(String image, Context context) {
                // Get file stream
                File file = new File(context.getFilesDir(), image);

                // Create options
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 6;

                return BitmapFactory.decodeFile(file.getPath(), options);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                mImageView.setImageBitmap(bitmap);
                this.cancel(true);
            }
        }


        @Override
        public void onClick(View view) {
            Log.d(GridAdapter.class.getSimpleName(), "onClick: ");
            // TODO: pass in image
            // Start intent
            Intent intent = new Intent(Intent.ACTION_VIEW);
            File file = new File(mContext.getFilesDir(), mImageName);

            Log.d(TAG, "onClick: " + file.getPath());

            intent.setDataAndType(Uri.parse("file://" + file.getAbsolutePath()), "image/*");

            view.getContext().startActivity(intent);
        }
    }
}
