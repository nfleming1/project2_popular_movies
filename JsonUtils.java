package com.example.project2_popular_movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static void parseMovieJson(String json, Movie movieObject) {
        try {
            //System.out.print("got this far before escaping");
            JSONObject JsonMovie = new JSONObject(json);

            String MovieRating = JsonMovie.getString("vote_average");
            movieObject.setRating(MovieRating);
            String MovieTitle = JsonMovie.getString("title");
            movieObject.setTitle(MovieTitle);
            String MoviePoster = JsonMovie.getString("poster_path");
            movieObject.setPoster_path(MoviePoster);
            String MovieSynopsis = JsonMovie.getString("overview");
            movieObject.setSynopsis(MovieSynopsis);
            String MovieReleaseDate = JsonMovie.getString("release_date");
            movieObject.setRelease_date(MovieReleaseDate);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
