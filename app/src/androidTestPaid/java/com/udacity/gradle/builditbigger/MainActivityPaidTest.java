package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class MainActivityPaidTest {

    @Rule
    public ActivityTestRule<MainActivity> mRecipeActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickingJokeButton_shouldGetJoke(){
        /* 1. Click the joke button to trigger the async task:
        *     Note this triggers espresso to wait
        *     for the async task to complete */
        onView(withId(R.id.joke_button)).perform(click());

        /* 2. Retrieve the joke from the activity */
        String retrievedJoke = mRecipeActivityTestRule.getActivity().mJoke;
        Log.d("MainActivityPaidTest", "retrievedJoke is: " + retrievedJoke);

        /* 3. Assert the joke is neither null or empty */
        assertNotNull(retrievedJoke);
        assertFalse(retrievedJoke.isEmpty());
    }

}
