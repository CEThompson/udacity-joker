package com.udacity.gradle.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isRoot;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityPaidTest {

    @Rule
    public ActivityTestRule<MainActivity> mRecipeActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    private IdlingRegistry mIdlingRegistry;

    @Test
    public void clickJokeButton_startsAsyncTask(){
        // Clicking joke button starts the async task
        onView(withId(R.id.joke_button)).perform(click());
    }

    @Before
    public void registerIdlingResource(){
        mIdlingResource = mRecipeActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry = IdlingRegistry.getInstance();
        mIdlingRegistry.register(mIdlingResource);
    }

    @Test
    public void afterIdling_shouldDisplayJoke(){
        String retrievedJoke = mRecipeActivityTestRule.getActivity().mJoke;
        assert(retrievedJoke != null && !retrievedJoke.equals(""));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource!=null){
            mIdlingRegistry.unregister(mIdlingResource);
        }
    }

}
