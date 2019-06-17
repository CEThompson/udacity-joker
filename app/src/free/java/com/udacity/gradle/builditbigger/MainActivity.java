package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.jokedisplay.DisplayActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.OnJokeRetrievedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    private ProgressBar mPb;
    private Button mButton;
    private TextView mTv;

    /* This block used for testing */
    @Nullable
    private CountingIdlingResource mIdlingResource;
    @VisibleForTesting
    @Nullable
    public CountingIdlingResource getIdlingResource(){
        if (mIdlingResource == null)
            mIdlingResource = new CountingIdlingResource("name");
        return mIdlingResource;
    }
    private void incrementIdlingResource(){
        if (mIdlingResource!=null)
            mIdlingResource.increment();
    }
    private void decrementIdlingResource(){
        if (mIdlingResource!=null)
            mIdlingResource.decrement();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPb = findViewById(R.id.loading_joke_progress_bar);
        mButton = findViewById(R.id.joke_button);
        mTv = findViewById(R.id.instructions_text_view);

        // Set up the interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoading();
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void tellJoke(View view) {
        final Context context = this;
        // For beginning the ad test
        incrementIdlingResource();

        // If the interstitial ad is already loaded
        // Set a listener for its close and show it
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.setAdListener(new AdListener(){
                @Override
                public void onAdClosed() {
                    // For beginning joke retrieval test
                    incrementIdlingResource();

                    showLoading();
                    EndpointsAsyncTask jokeTask = new EndpointsAsyncTask(context);
                    jokeTask.execute();
                }
            });

            mInterstitialAd.show();

            // For ending the ad test
            decrementIdlingResource();
        }

        // Otherwise set a listener for its close then
        // Set a listener for the ad loading
        // And hide the button
        else {
            showLoading();
            mInterstitialAd.setAdListener(new AdListener() {
                @Override
                public void onAdClosed() {
                    // For beginning joke retrieval test
                    incrementIdlingResource();

                    showLoading();
                    EndpointsAsyncTask jokeTask = new EndpointsAsyncTask(context);
                    jokeTask.execute();
                }

                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    mInterstitialAd.show();

                    // For ending the ad test
                    decrementIdlingResource();
                }
            });
        }
    }

    /* Async task uses this method to hide the loading bar and
     start the joke display activity */
    public void taskComplete(String jokeResult) {
        /* This block used to handle loading bar and activity */
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(getString(R.string.joke_key), jokeResult);
        startActivity(intent);

        // For ending the joke retrieval test
        decrementIdlingResource();
    }

    public void showLoading(){
        mPb.setVisibility(View.VISIBLE);
        mButton.setVisibility(View.INVISIBLE);
        mTv.setVisibility(View.INVISIBLE);
    }

    public void hideLoading(){
        mPb.setVisibility(View.INVISIBLE);
        mButton.setVisibility(View.VISIBLE);
        mTv.setVisibility(View.VISIBLE);
    }
}
