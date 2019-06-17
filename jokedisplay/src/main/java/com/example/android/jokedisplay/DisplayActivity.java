package com.example.android.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tv = findViewById(R.id.joke_tv);

        Intent intent = getIntent();

        String joke;

        if (intent.hasExtra(getString(R.string.joke_key)))
            joke = intent.getStringExtra(getString(R.string.joke_key));
        else
            joke = getString(R.string.intent_error);

        if (joke.isEmpty())
            joke = getString(R.string.library_error);

        tv.setText(joke);

    }
}
