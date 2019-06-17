package com.udacity.gradle.builditbigger;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.jokedisplay.DisplayActivity;

public class MainActivity extends AppCompatActivity implements EndpointsAsyncTask.OnJokeRetrievedListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressBar mPb;
    private Button mButton;
    private TextView mTv;

    /* This block variable used for testing */
    public String mJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPb = findViewById(R.id.loading_joke_progress_bar);
        mButton = findViewById(R.id.joke_button);
        mTv = findViewById(R.id.instructions_text_view);

    }

    @Override
    protected void onResume() {
        super.onResume();
        hideLoading();
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
        showLoading();
        EndpointsAsyncTask jokeTask = new EndpointsAsyncTask(this);
        jokeTask.execute();
    }

    /* Async task uses this method to hide the loading bar and
     start the joke display activity */
    public void taskComplete(String jokeResult) {
        /* This used to test the async task result */
        mJoke = jokeResult;

        /* This block used to handle loading bar and activity */
        Intent intent = new Intent(this, DisplayActivity.class);
        intent.putExtra(getString(R.string.joke_key), jokeResult);
        //hideLoading(); // hide the loading indicator
        startActivity(intent); // then start the activity
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
