package com.example.shruj.imdbapp;


import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by shruj on 02/24/2016.
 */
public class MovieUtil {
    static public class MovieJSONParser {
        static Movies parseMovie(String inputStreamString, Movies movies) throws JSONException {
            JSONObject jsonObjectRoot = new JSONObject(inputStreamString);
            movies = Movies.updateMovieDetailsFromJSON(jsonObjectRoot, movies);
            return movies;
        }
    }
}
