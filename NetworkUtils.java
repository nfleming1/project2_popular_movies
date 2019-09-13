package com.example.project2_popular_movies;

import android.net.Uri;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    /*
    // buildURL(): query_type = true when query is POPULAR
    // // // // // query_type = false when query is RATING
    */
    public static URL buildUrl(Boolean query_type) {

        String TMDB_BASE_URL = "http://api.themoviedb.org/3/discover/movie?api_key=";
        String API_KEY = "";
        String POPULAR_QUERY = "&language=en-US&sort_by=popularity.desc&primary_release_date.gte=2019&vote_count.gte=700&page=1";
        String RATING_QUERY = "&language=en-US&sort_by=vote_average.desc&primary_release_date.gte=2019&vote_count.gte=700&page=1";

        String builtURL = TMDB_BASE_URL.concat(API_KEY);
        if(query_type == true)
            builtURL = builtURL.concat(POPULAR_QUERY);
        else
            builtURL = builtURL.concat(RATING_QUERY);

        //System.out.println(builtURL);
        URL url = null;
        try {
            url = new URL(builtURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}