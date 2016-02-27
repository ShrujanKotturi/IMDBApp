package com.example.shruj.imdbapp;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by shruj on 02/24/2016.
 */
public class Movies implements Serializable {
    String movieTitle;
    String year;
    String poster;
    String released;
    String genre;
    String director;
    String actors;
    String plot;
    String imdbRating;
    String imdbId;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getReleased() {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM dd yyyy");
//        return simpleDateFormat.format(released);
        return this.released;
    }

    public void setReleased(String released) {
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM yyyy");
//        this.released = simpleDateFormat.format(simpleDateFormat);
        this.released = released;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    @Override
    public String toString() {
        return "Movies{" +
                "movieTitle='" + movieTitle + '\'' +
                ", year='" + year + '\'' +
                ", poster='" + poster + '\'' +
                ", released='" + released + '\'' +
                ", genre='" + genre + '\'' +
                ", director='" + director + '\'' +
                ", actors='" + actors + '\'' +
                ", plot='" + plot + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbId='" + imdbId + '\'' +
                '}';
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String imdbRating) {
        this.imdbRating = imdbRating;
    }

    public static Movies createMovieFromJSON(JSONObject movieJSONObject) throws JSONException {
        Movies movies = new Movies();
        try {
            movies.setMovieTitle(movieJSONObject.getString(Constants.JSON_ROOT_TITLE));
            movies.setYear(movieJSONObject.getString(Constants.JSON_ROOT_YEAR));
            movies.setImdbId(movieJSONObject.getString(Constants.JSON_ROOT_IMDBID));
            movies.setPoster(movieJSONObject.getString(Constants.JSON_ROOT_POSTER));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public static Movies updateMovieDetailsFromJSON(JSONObject movieJSONObject, Movies movies) {

        try {
            movies.setReleased(movieJSONObject.getString(Constants.JSON_ROOT_RELEASED));
            movies.setGenre(movieJSONObject.getString(Constants.JSON_ROOT_GENRE));
            movies.setDirector(movieJSONObject.getString(Constants.JSON_ROOT_DIRECTOR));
            movies.setActors(movieJSONObject.getString(Constants.JSON_ROOT_ACTORS));
            movies.setPlot(movieJSONObject.getString(Constants.JSON_ROOT_PLOT));
            movies.setImdbRating(movieJSONObject.getString(Constants.JSON_ROOT_IMDB_RATING));
            return movies;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
