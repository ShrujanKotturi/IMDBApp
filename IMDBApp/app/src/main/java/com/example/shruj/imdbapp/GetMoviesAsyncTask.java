package com.example.shruj.imdbapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by shruj on 02/24/2016.
 */
public class GetMoviesAsyncTask extends AsyncTask<String, Integer, ArrayList<Movies>> {

    Activity activity;
    ProgressDialog progressDialog;

    public GetMoviesAsyncTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected ArrayList<Movies> doInBackground(String... params) {

        RequestParam requestParam = new RequestParam(Constants.GET_METHOD, Constants.OMDB_API_URL);
        requestParam.addParam(Constants.TYPE_NAME_URL, Constants.MOVIE_TAG_URL);
        requestParam.addParam(Constants.URL_S_TAG, params[0]);

        try {
            HttpURLConnection httpURLConnection = requestParam.setUpConnection();
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line = bufferedReader.readLine();
                while (line != null) {
                    stringBuilder.append(line);
                    line = bufferedReader.readLine();
                }
                try {
                    return MoviesUtil.MoviesJSONParser.parseMovies(stringBuilder.toString());
                } catch (JSONException e) {
                    Log.d("demo", e.getMessage());
                    Log.d("demo", e.getLocalizedMessage());
                    e.printStackTrace();
                    return null;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage(Constants.PROGRESS_DIALOG_MESSAGE);
        progressDialog.setMax(100);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(Boolean.FALSE);
        progressDialog.show();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Movies> movies) {
        super.onPostExecute(movies);
        progressDialog.dismiss();
        if (movies != null) {
            setSearchMoviesActivityUIElements(movies);
        } else {
            activity.finish();
            Toast.makeText(activity, Constants.WRONG_MOVIE_NAME, Toast.LENGTH_SHORT).show();
        }

    }

    private void setSearchMoviesActivityUIElements(final ArrayList<Movies> movies) {
        LinearLayout linearLayoutMovies = (LinearLayout) activity.findViewById(R.id.linearLayoutMovies);
        int count = 0;
        for (final Movies individualMovieItem : movies) {
            final TextView textView = new TextView(activity);
            textView.setText(individualMovieItem.getMovieTitle() + " (" + individualMovieItem.getYear() + ")");
            textView.setClickable(true);
            textView.setTextSize(22);
            textView.setTextColor(Color.BLACK);
            textView.setTag(count);

            textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, MovieDetailActivity.class);
                    intent.putExtra(Constants.INTENT_IMDB_CURRENT, (Integer) textView.getTag());
                    intent.putExtra(Constants.INTENT_MOVIES_OBJECT_TO_MOVIE_DETAILS, movies);
                    activity.startActivity(intent);
                }
            });
            linearLayoutMovies.addView(textView);
            count++;
        }
    }
}
