package com.example.shruj.imdbapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieDetailActivity extends AppCompatActivity {
    Intent intent;
    ArrayList<Movies> moviesArrayList = new ArrayList<>();
    static ArrayList<Movies> arrayListMovies = new ArrayList<>();
    public static TextView textViewMovieName, textViewReleaseDateValue, textViewGenreValue, textViewDirectorValue, textViewActorValue, textViewPlotDescription;
    static RatingBar ratingBarMovie;
    static ImageView imageViewMovie;

    int totalNumberOfMovieObjects = 0;
    static int currentMovieObjectNumber = -1;


    static String selectedIMDbId;
    static Movies selectedMovieObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(Boolean.TRUE);
        actionBar.setIcon(R.drawable.ic_launcher);


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
            selectedIMDbId = intent.getExtras().getString(Constants.INTENT_IMDB_CURRENT);
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

        for (Movies movieItem : arrayListMovies) {
            currentMovieObjectNumber++;
            if (movieItem.getImdbId().equals(selectedIMDbId)) {
                //selectedMovieObject = movieItem;
                break;
            }
        }

        setUI();

    }

    public void setUI() {

        try {

            if (!arrayListMovies.isEmpty()) {

                int count = 0;
                totalNumberOfMovieObjects = arrayListMovies.size();
                if ((currentMovieObjectNumber + 1) > totalNumberOfMovieObjects) {
                    currentMovieObjectNumber = 0;
                } else if (currentMovieObjectNumber < 0) {
                    currentMovieObjectNumber = totalNumberOfMovieObjects - 1;
                }

                for (Movies movies : arrayListMovies) {
                    if (count == currentMovieObjectNumber) {
                        selectedMovieObject = movies;
                        break;
                    } else {
                        count++;
                    }
                }

                textViewMovieName.setText(selectedMovieObject.getMovieTitle() + " (" + selectedMovieObject.getYear() + ")");
                textViewReleaseDateValue.setText("  " + selectedMovieObject.getReleased());
                textViewGenreValue.setText("  " + selectedMovieObject.getGenre());
                textViewDirectorValue.setText("  " + selectedMovieObject.getDirector());
                textViewActorValue.setText("  " + selectedMovieObject.getActors());
                textViewPlotDescription.setText("  " + selectedMovieObject.getPlot());
                ratingBarMovie.setRating((Float.parseFloat(selectedMovieObject.getImdbRating())) / 2);
                ratingBarMovie.setClickable(Boolean.FALSE);

                if (!selectedMovieObject.getPoster().equals(Constants.NOT_APPLICABLE)) {
                    new GetImageAsyncTask(this).execute(selectedMovieObject.getPoster());
                    imageViewMovie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MovieDetailActivity.this, MovieWebviewActivity.class);
                            intent.putExtra(Constants.INTENT_IMDB_ID, selectedMovieObject.getImdbId());
                            startActivity(intent);
                        }
                    });
                } else {
                    imageViewMovie.setClickable(Boolean.FALSE);
                    imageViewMovie.setImageBitmap(null);
                    Constants.ToastMessages(MovieDetailActivity.this, Constants.IMAGE_NOT_FOUND);
                }


            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(Bitmap bitmap) {
        imageViewMovie.setImageBitmap(bitmap);
    }

}
