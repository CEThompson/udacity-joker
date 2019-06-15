package com.udacity.gradle.builditbigger;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityFreeTest {

    @Rule
    public ActivityTestRule<MainActivity> mRecipeActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    /* This test begins the async task to retrieve the joke */
    @Test
    public void clickJokeButton_startsAsyncTask(){
        // Clicking joke button starts the interstitial ad
        onView(withId(R.id.joke_button)).perform(click());
        // Closing the ad begins the async task
        onView(isRoot()).perform(click());
    }

    @Test
    public void afterIdling_shouldDisplayJoke(){
        String retrievedJoke = mRecipeActivityTestRule.getActivity().mJoke;
        assert(retrievedJoke != null && !retrievedJoke.equals(""));
    }

}
