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

        TextView tv = findViewById(R.id.joke_tv);

        Intent intent = getIntent();
        String joke = intent.getStringExtra(joke_key);

        tv.setText(joke);

    }
}
