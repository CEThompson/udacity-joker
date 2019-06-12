package com.example.android.jokedisplay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {

    public static final String joke_key = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        TextView tv = (TextView) findViewById(R.id.joke_tv);


        String joke = "no joke set";

        Intent intent = getIntent();
        joke = intent.getStringExtra(joke_key);

        tv.setText(joke);

    }
}
