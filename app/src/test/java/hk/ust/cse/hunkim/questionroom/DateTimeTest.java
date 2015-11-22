package hk.ust.cse.hunkim.questionroom;

import android.test.suitebuilder.annotation.SmallTest;
import junit.framework.TestCase;
import hk.ust.cse.hunkim.questionroom.question.Question;

/**
 * Created by User on 23-Nov-2015.
 */
public class DateTimeTest extends TestCase {
    protected void setUp() throws Exception {
        super.setUp();
    }

    @SmallTest
    public void testgetTimeAgo() {
        final int SECOND_MILLIS = 1000;
        final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        final int DAY_MILLIS = 24 * HOUR_MILLIS;

        long timeNow = (long) System.currentTimeMillis();
        assertEquals("just now", QuestionListAdapter.getTimeAgo(timeNow));
        assertEquals("a minute ago", QuestionListAdapter.getTimeAgo(timeNow - MINUTE_MILLIS));
        assertEquals("10 minutes ago", QuestionListAdapter.getTimeAgo(timeNow - 10 * MINUTE_MILLIS));
        assertEquals("an hour ago", QuestionListAdapter.getTimeAgo(timeNow - HOUR_MILLIS));
        assertEquals("10 hours ago", QuestionListAdapter.getTimeAgo(timeNow - 10 * HOUR_MILLIS));
        assertEquals("yesterday", QuestionListAdapter.getTimeAgo(timeNow - 30 * HOUR_MILLIS));
        assertEquals("10 days ago", QuestionListAdapter.getTimeAgo(timeNow - 10 * DAY_MILLIS));

        QuestionListAdapter.getTimeAgo(1000000000000L);
        QuestionListAdapter.getTimeAgo(1000000000000L - 1);
    }
}


