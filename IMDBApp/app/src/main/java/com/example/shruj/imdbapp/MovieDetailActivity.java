package com.example.shruj.imdbapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MovieDetailActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<Movies> moviesArrayList = new ArrayList<>();
    static ArrayList<Movies> arrayListMovies = new ArrayList<>();
    public static TextView textViewMovieName, textViewReleaseDateValue, textViewGenreValue, textViewDirectorValue, textViewActorValue, textViewPlotDescription;
    static RatingBar ratingBarMovie;
    static ImageView imageViewMovie;
    Context context;

    int totalNumberOfMovieObjects = 0;
    static int currentMovieObjectNumber = -1;

    static Movies selectedMovieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(Boolean.TRUE);
        actionBar.setIcon(R.drawable.ic_launcher);
        context = getApplicationContext();


        textViewMovieName = (TextView) findViewById(R.id.textViewMovieName);
        textViewReleaseDateValue = (TextView) findViewById(R.id.textViewReleaseDateValue);
        textViewGenreValue = (TextView) findViewById(R.id.textViewGenreValue);
        textViewDirectorValue = (TextView) findViewById(R.id.textViewDirectorValue);
        textViewActorValue = (TextView) findViewById(R.id.textViewActorValue);
        textViewPlotDescription = (TextView) findViewById(R.id.textViewPlotDescription);
        ratingBarMovie = (RatingBar) findViewById(R.id.ratingBarMovie);
        imageViewMovie = (ImageView) findViewById(R.id.imageViewMovie);
        intent = getIntent();

        if (intent.getExtras() != null) {
            currentMovieObjectNumber = intent.getExtras().getInt(Constants.INTENT_IMDB_CURRENT);
            moviesArrayList = (ArrayList<Movies>) intent.getExtras().getSerializable(Constants.INTENT_MOVIES_OBJECT_TO_MOVIE_DETAILS);
            new GetIndividualMovieAsyncTask(this).execute(moviesArrayList);
        }


        findViewById(R.id.buttonFinish).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.imageViewNext).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMovieObjectNumber++;
                setUI();
            }
        });
        findViewById(R.id.imageViewPrevious).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentMovieObjectNumber--;
                setUI();
            }
        });
    }


    public void setMoviesDetailsActivityUIElements(final ArrayList<Movies> movies) {
        arrayListMovies = movies;
        setUI();
    }

    public void setUI() {
        try {
            if (!arrayListMovies.isEmpty()) {
                totalNumberOfMovieObjects = arrayListMovies.size();
                if ((currentMovieObjectNumber + 1) > totalNumberOfMovieObjects) {
                    currentMovieObjectNumber = 0;
                } else if (currentMovieObjectNumber < 0) {
                    currentMovieObjectNumber = totalNumberOfMovieObjects - 1;
                }

                selectedMovieObject = arrayListMovies.get(currentMovieObjectNumber);

                textViewMovieName.setText(selectedMovieObject.getMovieTitle() + " (" + selectedMovieObject.getYear() + ")");
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
                    Date releasedDate = simpleDateFormat.parse(selectedMovieObject.getReleased());
                    simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
                    textViewReleaseDateValue.setText(" " + simpleDateFormat.format(releasedDate));
                } catch (Exception e) {
                    textViewReleaseDateValue.setText(Constants.NOT_APPLICABLE);
                    e.printStackTrace();
                }
                textViewGenreValue.setText("  " + selectedMovieObject.getGenre());
                textViewDirectorValue.setText("  " + selectedMovieObject.getDirector());
                textViewActorValue.setText("  " + selectedMovieObject.getActors());
                textViewPlotDescription.setText("  " + selectedMovieObject.getPlot());
                ratingBarMovie.setClickable(Boolean.FALSE);
                try {
                    ratingBarMovie.setRating((Float.parseFloat(selectedMovieObject.getImdbRating())) / 2);
                } catch (Exception e) {
                    ratingBarMovie.setRating(0.0f);
                    e.printStackTrace();
                }


                if (!selectedMovieObject.getPoster().equals(Constants.NOT_APPLICABLE)) {
                    new GetImageAsyncTask().execute(selectedMovieObject.getPoster());
                    imageViewMovie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MovieWebviewActivity.class);
                            intent.putExtra(Constants.INTENT_IMDB_ID, selectedMovieObject.getImdbId());
                            startActivity(intent);
                        }
                    });
                } else {
                    imageViewMovie.setClickable(Boolean.FALSE);
                    imageViewMovie.setImageBitmap(null);
                    Constants.ToastMessages(context, Constants.IMAGE_NOT_FOUND);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(Bitmap bitmap) {
        imageViewMovie.setImageBitmap(bitmap);
    }

}
