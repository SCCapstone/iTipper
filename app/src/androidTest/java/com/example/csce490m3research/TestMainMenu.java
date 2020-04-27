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
    public void setUp() {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        FirebaseAuth.getInstance().signInAnonymously();
    }

    @Test
    public void databaseReferences() {
        assertNotNull(Database.shiftsReference());
        assertNotNull(Database.tipsReference());
    }

    @Test
    public void buttonsDisplayed() {
        onView(withId(R.id.welcomeMessage)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.view_all_tips_button)).check(matches(ViewMatchers.isDisplayed()));
        onView(withId(R.id.goToShiftScreen)).check(matches(isDisplayed()));
        onView(withId(R.id.view_activity_button)).check(matches(isDisplayed()));
    }

    @Test
    public void getUid() {
        String uid = Database.getUID();
        String uid2 = FirebaseAuth.getInstance().getUid();

        assertEquals(uid, uid2);
    }

    @Test
    public void changeFragments() {
        onView(withId(R.id.nav_graph)).perform(click());
        onView(withId(R.id.plot)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.nav_settings)).perform(click());
        onView(withId(R.id.logout_button)).check(matches(ViewMatchers.isDisplayed()));

        onView(withId(R.id.nav_home)).perform(click());
        onView(withId(R.id.welcomeMessage)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void navigateShiftScreen() {
        onView(withId(R.id.goToShiftScreen)).perform(click());
        onView(withId(R.id.enter_tip_screen)).check(matches(isDisplayed()));
    }

    @Test
    public void testLogout() {
        onView(withId(R.id.nav_settings)).perform(click());
        onView(withId(R.id.logout_button)).perform(click());
        assertNull(FirebaseAuth.getInstance().getUid());
    }

    @Test
    public void backButton() {
        onView(withId(R.id.goToShiftScreen)).perform(click());
        onView(withContentDescription("Navigate up")).perform(click());
        onView(withId(R.id.welcomeMessage)).check(matches(isDisplayed()));
    }
}
