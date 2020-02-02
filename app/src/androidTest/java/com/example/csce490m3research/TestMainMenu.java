package com.example.csce490m3research;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
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

    /**
     * Check if the button to navigate to the graphs screen is displayed to the user.
     */
    @Test
    public void testIsDisplayed() {
        onView(withId(R.id.graph_button))
                .check(matches(isDisplayed()));
    }

    /**
     * Check if clicking the button to navigate to the graphs screen takes the user
     * to the appropriate screen.
     */
    @Test
    public void testNavigation() {
        onView(withId(R.id.graph_button)).perform(click());
        onView(withId(R.id.chooseGraphText)).check(matches(isDisplayed()));
    }
}
