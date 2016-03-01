package com.example.shruj.imdbapp;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by shruj on 03/01/2016.
 */
public interface IActivity {
    void setMoviesDetailsActivityUIElements(final ArrayList<Movies> movies);

    void setImage(Bitmap bitmap);
}
