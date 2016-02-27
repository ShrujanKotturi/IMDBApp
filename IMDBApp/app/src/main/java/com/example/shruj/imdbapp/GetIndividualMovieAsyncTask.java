package com.example.shruj.imdbapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

/**
 * Created by shruj on 02/24/2016.
 */
public class GetIndividualMovieAsyncTask extends AsyncTask<ArrayList<Movies>, Integer, ArrayList<Movies>> {
    Activity activity;

    public GetIndividualMovieAsyncTask(Activity activity) {
        this.activity = activity;
    }

    ProgressDialog progressDialog;
    ArrayList<Movies> moviesArrayList = new ArrayList<>();

    @Override
    protected ArrayList<Movies> doInBackground(ArrayList<Movies>... params) {
        for (Movies movie : params[0]) {
            RequestParam requestParam = new RequestParam(Constants.GET_METHOD, Constants.OMDB_API_URL);
            try {
                requestParam.addParam(Constants.URL_I_TAG, movie.getImdbId());
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

                    moviesArrayList.add(MovieUtil.MovieJSONParser.parseMovie(stringBuilder.toString(), movie));
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return moviesArrayList;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(activity);
        progressDialog.setMax(100);
        progressDialog.setCancelable(Boolean.FALSE);
        progressDialog.setMessage(Constants.PROGRESS_DIALOG_LOADING_MOVIE);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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

        if (movies != null) {
            setMoviesDetailsActivityUIElements(movies);
        }

        progressDialog.dismiss();

    }

    private void setMoviesDetailsActivityUIElements(final ArrayList<Movies> movies) {
        new MovieDetailActivity().setMoviesDetailsActivityUIElements(movies);
    }


}


