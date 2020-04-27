package com.example.csce490m3research;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static androidx.test.espresso.Espresso.*;

@RunWith(AndroidJUnit4.class)
public class LoginInstrumentedTest {

    @Rule
    public ActivityTestRule<LoginActivity> activityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        FirebaseAuth.getInstance().signOut();
    }

    @Test
    public void displayed() {
        onView(withId(R.id.login_activity)).check(matches(isDisplayed()));
    }

    @Test
    public void failsWhenBoxesLeftEmpty() {
        onView(withId(R.id.login_button)).perform(click());
        assertEquals("default_user", Database.getUID());
    }
}
