package com.example.shruj.imdbapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by shruj on 02/24/2016.
 */
public class MoviesUtil {
    static public class MoviesJSONParser {
        static ArrayList<Movies> parseMovies(String inputStreamString) throws JSONException {
            ArrayList<Movies> arrayListMovies = new ArrayList<>();

            JSONObject jsonObjectRoot = new JSONObject(inputStreamString);
            JSONArray jsonArrayMovies = jsonObjectRoot.getJSONArray(Constants.JSON_ROOT_NAME);

            for (int i = 0; i < jsonArrayMovies.length(); i++) {
                JSONObject movieJSONObject = jsonArrayMovies.getJSONObject(i);
                Movies movies = Movies.createMovieFromJSON(movieJSONObject);
                arrayListMovies.add(movies);
            }

            Collections.sort(arrayListMovies, new moviesSortBasedOnYear());
            return arrayListMovies;
        }

        private static class moviesSortBasedOnYear implements java.util.Comparator<Movies> {
            @Override
            public int compare(Movies lhs, Movies rhs) {
                return rhs.getYear().compareTo(lhs.getYear());
            }
        }
    }


}
