package com.example.shruj.imdbapp;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by shruj on 02/24/2016.
 */
public class Constants {

    public static String MOVIE_NAME = "MovieName";

    //JSON related
    public static String JSON_ROOT_NAME = "Search";
    public static String JSON_ROOT_TITLE = "Title";
    public static String JSON_ROOT_YEAR = "Year";
    public static String JSON_ROOT_IMDBID = "imdbID";
    public static String JSON_ROOT_POSTER = "Poster";
    public static String JSON_ROOT_RELEASED = "Released";
    public static String JSON_ROOT_GENRE = "Genre";
    public static String JSON_ROOT_DIRECTOR = "Director";
    public static String JSON_ROOT_ACTORS = "Actors";
    public static String JSON_ROOT_PLOT = "Plot";
    public static String JSON_ROOT_IMDB_RATING = "imdbRating";

    //API URL related
    public static String OMDB_API_URL = "http://www.omdbapi.com/";
    public static String GET_METHOD = "GET";
    public static String POST_METHOD = "POST";
    public static String TYPE_NAME_URL = "type";
    public static String MOVIE_TAG_URL = "movie";
    public static String URL_S_TAG = "s";

    //INDIVIDUAL MOVIE URL RELATED
    public static String URL_I_TAG = "i";

    //WEB VIEW URL RELATED
    public static String URL_WEB_VIEW = "http://m.imdb.com/title/";

    //Progress Dialog
    public static String PROGRESS_DIALOG_MESSAGE = "Loading Movie List..";
    public static String PROGRESS_DIALOG_LOADING_MOVIE = "Loading Movie..";

    //Intent related
    public static String INTENT_IMDB_CURRENT = "IMDbId";
    public static String INTENT_MOVIES_OBJECT_TO_MOVIE_DETAILS = "MovieObject";
    public static String INTENT_IMDB_ID = "IMDbId";

    //N/A
    public static String NOT_APPLICABLE = "N/A";

    //Toast Messages Strings

    //Information
    public static String IMAGE_NOT_FOUND = "Image not available";

    //Error
    public static String ERROR_MOVIE_NAME = "Please enter a movie name.";
    public static String NO_INTERNET_CONNECTION = "No available network connection";
    public static String WRONG_MOVIE_STRING = "No value for Search";
    public static String WRONG_MOVIE_NAME = "No movies found, Enter another movie name and Try again";

    //Toast Messages
    public static void ToastMessages(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

}
