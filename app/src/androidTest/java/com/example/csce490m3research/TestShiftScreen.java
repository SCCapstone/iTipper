package com.example.csce490m3research;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.hamcrest.Matchers.*;

import java.util.LinkedList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class TestShiftScreen {

    @Rule
    public ActivityTestRule<EnterTipScreenActivity> activityRule =
            new ActivityTestRule<>(EnterTipScreenActivity.class);

    @Before
    public void setUp() throws InterruptedException {
        activityRule.getActivity().getSupportFragmentManager().beginTransaction();
        FirebaseAuth.getInstance().signInAnonymously();

        String uid = Database.getUID();

        /* Clean tips from test user from database */
        FirebaseFirestore.getInstance().collection("tips")
                .whereEqualTo("uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                            qs.getReference().delete();
                        }
                    }
                });
    }

    @After
    public void tearDown() {
        /* Clean tips from test user from database */
        FirebaseFirestore.getInstance().collection("tips")
                .whereEqualTo("uid", Database.getUID())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                            qs.getReference().delete();
                        }
                    }
                });
    }

    @Test
    public void quickTip() throws InterruptedException {
        final String uid = Database.getUID();

        onView(withId(R.id.quicktip5)).perform(click());

        FirebaseFirestore.getInstance().collection("tips")
                .whereEqualTo("uid", uid)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot ds : queryDocumentSnapshots) {
                            assertEquals(5.0, ds.get("value"));
                        }
                    }
                });
    }

    @Test
    public void customTipSucceeds() {
        final String uid = Database.getUID();

        final double expected = 1.23;

        onView(withId(R.id.tipInputText)).perform(typeText("1.23"));
        onView(withId(R.id.add_custom_tip_button)).perform(click());

        FirebaseFirestore.getInstance().collection("tips")
                .whereEqualTo("uid", uid)
                .orderBy("time", Query.Direction.DESCENDING)
                .limit(1)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                            assertEquals(1.23, qs.get("value"));
                        }
                    }
                });
    }

    @Test
    public void customTipFails() {
        final String uid = Database.getUID();

        onView(withId(R.id.tipInputText)).perform(typeText("0"));
        onView(withId(R.id.add_custom_tip_button)).perform(click());

        FirebaseFirestore.getInstance().collection("tips")
                .whereEqualTo("uid", uid)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                        boolean tipFound = false;
                        for (QueryDocumentSnapshot qs : queryDocumentSnapshots) {
                            double value = (double) qs.get("value");
                            String docUid = (String) qs.get("uid");
                            if (value == 0.0 && docUid.equals(uid)) {
                                tipFound = true;
                            }
                        }
                        assertFalse(tipFound);
                    }
                });
    }

}
