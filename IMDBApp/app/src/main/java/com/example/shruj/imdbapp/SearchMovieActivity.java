package com.example.shruj.imdbapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class SearchMovieActivity extends AppCompatActivity {

    Intent intent;
    String movieName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(Boolean.TRUE);
        actionBar.setIcon(R.drawable.ic_launcher);

        intent = getIntent();

        if (intent.getExtras() != null) {
            movieName = intent.getExtras().getString(Constants.MOVIE_NAME);

            new GetMoviesAsyncTask(SearchMovieActivity.this).execute(movieName);
        }
    }
}
