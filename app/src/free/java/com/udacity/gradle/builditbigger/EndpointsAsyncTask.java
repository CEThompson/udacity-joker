package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

class EndpointsAsyncTask extends AsyncTask<Context, Void, String> {
    private static MyApi myApiService = null;
    private Context context;

    private static final String TAG = EndpointsAsyncTask.class.getSimpleName();

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

        try {
            Log.e(TAG, "Getting joke from service.");
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            Log.e(TAG, "Catching IO exception.");
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String joke) {
        MainActivity activity = (MainActivity) context;
        activity.taskComplete(joke);
    }

}

