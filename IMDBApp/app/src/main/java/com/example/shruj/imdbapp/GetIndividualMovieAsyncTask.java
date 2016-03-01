package com.example.shruj.imdbapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by shruj on 02/24/2016.
 */
public class GetIndividualMovieAsyncTask extends AsyncTask<ArrayList<Movies>, Integer, ArrayList<Movies>> {
    Activity activity;
    MovieDetailActivity movieDetailActivity = new MovieDetailActivity();

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

        movieDetailActivity.arrayListMovies = movies;

        if (!movieDetailActivity.arrayListMovies.isEmpty()) {
            movieDetailActivity.totalNumberOfMovieObjects = movieDetailActivity.arrayListMovies.size();
            if ((movieDetailActivity.currentMovieObjectNumber + 1) > movieDetailActivity.totalNumberOfMovieObjects) {
                movieDetailActivity.currentMovieObjectNumber = 0;
            } else if (movieDetailActivity.currentMovieObjectNumber < 0) {
                movieDetailActivity.currentMovieObjectNumber = movieDetailActivity.totalNumberOfMovieObjects - 1;
            }

            movieDetailActivity.selectedMovieObject = movieDetailActivity.arrayListMovies.get(movieDetailActivity.currentMovieObjectNumber);

            movieDetailActivity.textViewMovieName.setText(movieDetailActivity.selectedMovieObject.getMovieTitle() + " (" + movieDetailActivity.selectedMovieObject.getYear() + ")");
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                Date releasedDate = simpleDateFormat.parse(movieDetailActivity.selectedMovieObject.getReleased());
                simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
                movieDetailActivity.textViewReleaseDateValue.setText(" " + simpleDateFormat.format(releasedDate));
            } catch (Exception e) {
                movieDetailActivity.textViewReleaseDateValue.setText(Constants.NOT_APPLICABLE);
                e.printStackTrace();
            }
            movieDetailActivity.textViewGenreValue.setText("  " + movieDetailActivity.selectedMovieObject.getGenre());
            movieDetailActivity.textViewDirectorValue.setText("  " + movieDetailActivity.selectedMovieObject.getDirector());
            movieDetailActivity.textViewActorValue.setText("  " + movieDetailActivity.selectedMovieObject.getActors());
            movieDetailActivity.textViewPlotDescription.setText("  " + movieDetailActivity.selectedMovieObject.getPlot());
            movieDetailActivity.ratingBarMovie.setClickable(Boolean.FALSE);

            try {
                movieDetailActivity.ratingBarMovie.setRating((Float.parseFloat(movieDetailActivity.selectedMovieObject.getImdbRating())) / 2);
            } catch (Exception e) {
                movieDetailActivity.ratingBarMovie.setRating(0.0f);
                e.printStackTrace();
            }


            if (!movieDetailActivity.selectedMovieObject.getPoster().equals(Constants.NOT_APPLICABLE)) {
                new GetImageAsyncTask().execute(movieDetailActivity.selectedMovieObject.getPoster());
                movieDetailActivity.imageViewMovie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(activity, MovieWebviewActivity.class);
                        intent.putExtra(Constants.INTENT_IMDB_ID, movieDetailActivity.selectedMovieObject.getImdbId());
                        activity.startActivity(intent);
                    }
                });
            } else {
                movieDetailActivity.imageViewMovie.setClickable(Boolean.FALSE);
                movieDetailActivity.imageViewMovie.setImageBitmap(null);
                Constants.ToastMessages(activity, Constants.IMAGE_NOT_FOUND);
            }
        }
    }


}


