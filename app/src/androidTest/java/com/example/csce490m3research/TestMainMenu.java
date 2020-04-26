package com.example.csce490m3research;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TestMainMenu {

    @Rule
    public ActivityTestRule<MainMenuActivity> activityRule =
            new ActivityTestRule<>(MainMenuActivity.class);

    @Before
    public void init() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
    }

    @Test
    public void isDisplayed() {
        onView(withId(R.id.welcomeMessage)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.view_all_tips_button)).check(matches(ViewMatchers.isDisplayed()));
    }
}
