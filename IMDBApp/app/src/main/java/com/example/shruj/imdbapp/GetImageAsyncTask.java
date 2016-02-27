package com.example.shruj.imdbapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by shruj on 02/25/2016.
 */
public class GetImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

    Activity activity;

    public GetImageAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        URL url;
        Bitmap image = null;

        try {
            url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            image = BitmapFactory.decodeStream(con.getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        new MovieDetailActivity().setImage(bitmap);

    }
}
