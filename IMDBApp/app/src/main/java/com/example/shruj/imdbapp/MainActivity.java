package com.example.shruj.imdbapp;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    EditText editTextFindMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(Boolean.TRUE);
        actionBar.setIcon(R.drawable.ic_launcher);

        editTextFindMovies = (EditText) findViewById(R.id.editTextFindMovies);


        findViewById(R.id.buttonSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isConnectedOnline()) {
                    if (!editTextFindMovies.getText().toString().isEmpty()) {
                        intent = new Intent(MainActivity.this, SearchMovieActivity.class);
                        intent.putExtra(Constants.MOVIE_NAME, editTextFindMovies.getText().toString());
                        startActivity(intent);
                    } else {
                        editTextFindMovies.setError(Constants.ERROR_MOVIE_NAME);
                    }
                } else {
                    Constants.ToastMessages(MainActivity.this, Constants.NO_INTERNET_CONNECTION);
                }
            }
        });

    }

    private boolean isConnectedOnline() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }
}
