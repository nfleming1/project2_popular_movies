package com.example.project2_popular_movies;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    private ImageView mPosterIV;
    private TextView mTitleTV;
    private TextView mReleaseTV;
    private TextView mRatingTV;
    private TextView mSynopsisTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mPosterIV = (ImageView) findViewById(R.id.thumbnail_iv);
        mTitleTV = (TextView) findViewById(R.id.title_tv);
        mReleaseTV = (TextView) findViewById(R.id.release_date_tv);
        mRatingTV = (TextView) findViewById(R.id.rating_tv);
        mSynopsisTV = (TextView) findViewById(R.id.synopsis_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }
        Bundle extras = intent.getExtras();
        String release = "Release Date: "+extras.getString("RELEASE_DATE");
        String rating = "Rating: "+extras.getString("RATING")+"/10";

        //System.out.println(extras.getString("POSTER_PATH"));
        Picasso.with(this)
                .load(extras.getString("POSTER_PATH"))
                .into(mPosterIV);
        mPosterIV.setVisibility(View.VISIBLE);
        mTitleTV.setText(extras.getString("TITLE"));
        mReleaseTV.setText(release);
        mRatingTV.setText(rating);
        mSynopsisTV.setText(extras.getString("SYNOPSIS"));
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
