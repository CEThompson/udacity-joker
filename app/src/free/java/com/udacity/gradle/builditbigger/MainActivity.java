package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.udacity.gradle.builditbigger.test.SimpleIdlingResource;
import com.udacity.gradle.builditbigger.utils.keys;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private InterstitialAd mInterstitialAd;
    private ProgressBar mPb;
    private Button mButton;
    private TextView mTv;

    /* This block (variables and idling method) used for testing */
    @Nullable
    private SimpleIdlingResource mIdlingResource;
    public String mJoke;
    @VisibleForTesting
    @Nullable
    public IdlingResource getIdlingResource(){
        if (mIdlingResource == null){
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPb = findViewById(R.id.loading_joke_progress_bar);
        mButton = findViewById(R.id.joke_button);
        mTv = findViewById(R.id.instructions_text_view);

        // Initialize the idling resource for testing
        getIdlingResource();

        // Set up the interstitial ad
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
    }

    @Override
    protected void onResume() {
        super.onResume();
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

        // Load the joke after the ad
        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                showLoading();
                try { mIdlingResource.setIdleState(false);}
                catch (NullPointerException n){ Log.e(TAG, n.getMessage()); }
                new EndpointsAsyncTask().execute(context);
            }
        });

        if (mInterstitialAd.isLoaded())
            mInterstitialAd.show();
    }

    /* Async task uses this method to hide the loading bar and
     start the joke display activity */
    public void taskComplete(String jokeResult) {

        /* This block used to test the async task result */
        mJoke = jokeResult;
        try { mIdlingResource.setIdleState(true);}
        catch (NullPointerException n){ Log.e(TAG, n.getMessage()); }

        /* This block used to handle loading bar and activity */
        hideLoading();
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(keys.joke_key, jokeResult);
        startActivity(intent);
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
