package com.udacity.gradle.builditbigger.backend;

import com.example.android.jokeprovider.JokeProvider;

/** The object model for the data we are sending through endpoints */
public class MyBean {

    private String myData;

    public MyBean(){myData = new JokeProvider().getJoke();}

    public String getData() {
        return myData;
    }

    public void setData(String data) {
        myData = data;
    }
}