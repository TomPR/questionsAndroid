package hk.ust.cse.hunkim.questionroom;

import android.app.Instrumentation;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.widget.Button;
import android.widget.EditText;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.InstrumentationRegistry;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class JoinActivityUITest extends ActivityInstrumentationTestCase2<JoinActivity> {
    private JoinActivity activity;

    public JoinActivityUITest() {
        super(JoinActivity.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        activity = getActivity();
        assertNotNull(activity);
    }

    @Test
    public void testJoin() throws Exception {
        onView(withId(R.id.room_name)).perform(click());
        onView(withId(R.id.room_name)).perform(typeText("comp3111"), closeSoftKeyboard());
        onView(withId(R.id.join_button)).perform(click());
        onView(withId(R.id.close)).check(matches(isDisplayed()));
    }

    @Test
    public void testJoin_noText() throws Exception {
        onView(withId(R.id.join_button)).perform(click());
        onView(withId(R.id.room_name)).check(matches(isDisplayed()));
    }

    @Test
    public void testJoin_wrongcharacters() throws Exception {
        onView(withId(R.id.room_name)).perform(click());
        onView(withId(R.id.room_name)).perform(typeText("&$&#&?"),closeSoftKeyboard());
        onView(withId(R.id.join_button)).perform(click());
        onView(withId(R.id.room_name)).check(matches(isDisplayed()));
    }
}