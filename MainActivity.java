package com.example.project2_popular_movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    protected ImageView[] imageViewArray;
    protected Movie[] MovieArray;
    protected String[] PosterArray;
    protected TextView mAttributeTV;
    protected Button mPopularBtn;
    protected Button mRatingBtn;

    ProgressBar mLoadingIndicator;
    protected Boolean ORDER_FLAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAttributeTV = (TextView) findViewById(R.id.attributionTV);
        mAttributeTV.setText(R.string.attribution_text);
        mPopularBtn = (Button) findViewById(R.id.sort_popular_button);
        mRatingBtn = (Button) findViewById(R.id.sort_rated_button);
        mPopularBtn.setBackgroundColor(0x5555ff);
        mRatingBtn.setBackgroundColor(0x55ff55);
        /*
        ORDER_FLAG: popular=true, rating=false.
        By default, always start with popular order (true).
         */
        ORDER_FLAG = true;

        imageViewArray = new ImageView[9];
        MovieArray = new Movie[9];
        PosterArray = new String[9];

        for(int x=0; x<9; x++) {
            MovieArray[x] = new Movie();
        }
        imageViewArray[0] = (ImageView) findViewById(R.id.ItemA_iv);
        imageViewArray[1] = (ImageView) findViewById(R.id.ItemB_iv);
        imageViewArray[2] = (ImageView) findViewById(R.id.ItemC_iv);
        imageViewArray[3] = (ImageView) findViewById(R.id.ItemD_iv);
        imageViewArray[4] = (ImageView) findViewById(R.id.ItemE_iv);
        imageViewArray[5] = (ImageView) findViewById(R.id.ItemF_iv);
        imageViewArray[6] = (ImageView) findViewById(R.id.ItemG_iv);
        imageViewArray[7] = (ImageView) findViewById(R.id.ItemH_iv);
        imageViewArray[8] = (ImageView) findViewById(R.id.ItemI_iv);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);
        makeTMDBSearchQuery(ORDER_FLAG);
    }

    private void makeTMDBSearchQuery(Boolean query_type) {
        URL TMDBSearchUrl = NetworkUtils.buildUrl(query_type);
        new TMDBQueryTask().execute(TMDBSearchUrl);
    }

    private void populateMovies(String searchResults){
        try{
            JSONObject JsonMovie = new JSONObject(searchResults);
            JSONArray JsonResults = JsonMovie.getJSONArray("results");
            for(int x=0; x<9; x++)
            {
                //System.out.println(JsonResults.getString(x));
                JsonUtils.parseMovieJson(JsonResults.getString(x), MovieArray[x]);
                populatePosters(Movie.buildPosterPath(MovieArray[x].getPoster_path(), 's'), x);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void populatePosters(String newPoster_path, int position){
        //System.out.println(newPoster_path);
        Picasso.with(this).load(newPoster_path).into(imageViewArray[position]);
        imageViewArray[position].setVisibility(View.VISIBLE);
    }

    public class TMDBQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String searchResults = null;
            try {
                searchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return searchResults;
        }

        @Override
        protected void onPostExecute(String TMDBSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (TMDBSearchResults != null && !TMDBSearchResults.equals("")){
                populateMovies(TMDBSearchResults);
            }
            /*
            else
                showErrorMessage();
            */
        }
    }

    public void clickEventA(View v) {
        launchDetailActivity(0);
    }
    public void clickEventB(View v) {
        launchDetailActivity(1);
    }
    public void clickEventC(View v) {
        launchDetailActivity(2);
    }
    public void clickEventD(View v) {
        launchDetailActivity(3);
    }
    public void clickEventE(View v) {
        launchDetailActivity(4);
    }
    public void clickEventF(View v) {
        launchDetailActivity(5);
    }
    public void clickEventG(View v) {
        launchDetailActivity(6);
    }
    public void clickEventH(View v) {
        launchDetailActivity(7);
    }
    public void clickEventI(View v) {
        launchDetailActivity(8);
    }

    private void launchDetailActivity(int position) {
        Intent intent = new Intent(this, DetailActivity.class);
        Bundle extras = new Bundle();
        String poster = Movie.buildPosterPath(MovieArray[position].getPoster_path(), 'l');

        extras.putString("POSTER_PATH", poster);
        extras.putString("TITLE", MovieArray[position].getTitle());
        extras.putString("RELEASE_DATE", MovieArray[position].getRelease_date());
        extras.putString("RATING", MovieArray[position].getRating());
        extras.putString("SYNOPSIS", MovieArray[position].getSynopsis());
        //intent.putExtra(DetailActivity.EXTRA_POSITION, position);
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void orderByPopular(View v){
        if(ORDER_FLAG == true)
            return;
        else
        {
            ORDER_FLAG=true;
            makeTMDBSearchQuery(ORDER_FLAG);
        }
    }
    public void orderByRating(View v){
        if(ORDER_FLAG == true)
        {
            ORDER_FLAG=false;
            makeTMDBSearchQuery(ORDER_FLAG);
        }
        else
            return;
    }
}
