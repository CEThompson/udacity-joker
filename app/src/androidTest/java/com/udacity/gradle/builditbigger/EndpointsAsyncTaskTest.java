package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

public class EndpointsAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mRecipeActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void asyncTask_shouldRetrieveJoke(){
        Context context = mRecipeActivityTestRule.getActivity();
        EndpointsAsyncTask task = new EndpointsAsyncTask(context);
        task.execute();

        // This view interaction triggers espresso to wait until the end of the async task
        onView(withId(R.id.joke_tv)).perform(click());

        String retrievedJoke = mRecipeActivityTestRule.getActivity().mJoke;
        //Log.d("EndpointsAsyncTaskTest", "retrieved joke = " + retrievedJoke);

        // Assert string is not null or empty
        assertNotNull(retrievedJoke);
        assertFalse(retrievedJoke.isEmpty());
    }

}
