package com.example.martin.recyclingapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by martinhuynh on 26/03/2018.
 */

public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;

    public DownloadImageAsyncTask(ImageView imageView){
        this.imageView = imageView;
    }

    protected Bitmap doInBackground(String...urls){
        String urldisplay = urls[0];
        Bitmap image = null;

        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            image = BitmapFactory.decodeStream(in);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return image;
    }

    protected void onPostExecute(Bitmap result){
        imageView.setImageBitmap(result);
    }

}
