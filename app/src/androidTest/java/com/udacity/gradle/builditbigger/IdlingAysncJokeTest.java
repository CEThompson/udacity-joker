package com.example.android.builditbigger;

import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.udacity.gradle.builditbigger.MainActivity;
import com.udacity.gradle.builditbigger.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;

@RunWith(AndroidJUnit4.class)
public class IdlingAysncJokeTest {

    @Rule
    public ActivityTestRule<MainActivity> mRecipeActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);

    private IdlingResource mIdlingResource;
    private IdlingRegistry mIdlingRegistry;

    @Before
    public void registerIdlingResource(){

        mIdlingResource = mRecipeActivityTestRule.getActivity().getIdlingResource();
        mIdlingRegistry = IdlingRegistry.getInstance();
        mIdlingRegistry.register(mIdlingResource);
    }

    @Test
    public void clickGetJokeButton_DisplayActivity(){

        /* Perform click on recycler view position 0 after loading from network */
        onView(withId(R.id.joke_button)).perform(click());
        onView(withId(R.id.joke_tv)).check(matches(withText(containsString("TEMPORARY HILARIOUS JOKE"))));
    }

    @After
    public void unregisterIdlingResource(){
        if (mIdlingResource!=null){
            mIdlingRegistry.unregister(mIdlingResource);
        }
    }

}
