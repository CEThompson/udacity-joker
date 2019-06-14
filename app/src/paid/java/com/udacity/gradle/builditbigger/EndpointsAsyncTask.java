package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.jokedisplay.DisplayActivity;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;
import com.udacity.gradle.builditbigger.utils.keys;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected String doInBackground(Context... params) {
        if (myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://" + BuildConfig.TEST_IP + ":8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                            request.setDisableGZipContent(true);
                        }
                    });
            myApiService = builder.build();
        }

        context = params[0];
        //String name = params[0].second;
        Log.e("EndpointsAsyncTask", "first is = " + context.toString());
        //Log.e("EndpointsAsyncTask", "name is = " + name);
        Log.e("EndpointsAsyncTask", "myApiService is null == " + String.valueOf((myApiService == null)));


        try {
            Log.e("EndpointsAsyncTask", "trying to return api service string");
            //return myApiService.sayHi(name).execute().getData();
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e("EndpointsAsyncTask", "catching IO exception e");
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        //Toast.makeText(context, joke, Toast.LENGTH_LONG).show();
        //Log.e("EndpointsAsyncTask", joke);


        Intent intent = new Intent(context, DisplayActivity.class);
        intent.putExtra(keys.joke_key, joke);
        context.startActivity(intent);
    }

    public interface callback {
        void idleComplete();
    }
}

