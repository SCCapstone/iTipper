package com.example.csce490m3research;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class TipInstrumentedTest {

    @Test
    public void testTips() throws InvalidTipException {
        String uid = FirebaseAuth.getInstance().getUid();
        Tip tip = new Tip(5.0);

        assert uid != null;
        assert uid.equals(tip.getUid());
    }
}
