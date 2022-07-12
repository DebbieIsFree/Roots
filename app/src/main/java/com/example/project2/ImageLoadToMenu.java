package com.example.project2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.view.PointerIcon;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

class ImageLoadToMenu extends AsyncTask<Void, Void, Bitmap> {

    private String url;
    private View view;

    public ImageLoadToMenu(String url, View view) {
        this.url = url;
        this.view = view;
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            URL urlConnection = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) urlConnection
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);
        view.setPointerIcon(PointerIcon.create(result, 0, 0));
    }

}
