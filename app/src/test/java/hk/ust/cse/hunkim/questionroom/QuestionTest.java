package hk.ust.cse.hunkim.questionroom;

import android.test.suitebuilder.annotation.SmallTest;

import junit.framework.TestCase;

import hk.ust.cse.hunkim.questionroom.question.Question;


/**
 * Created by hunkim on 7/15/15.
 */

public class QuestionTest  extends TestCase {
    Question q;



    protected void setUp() throws Exception {
        super.setUp();

        q = new Question("Hello? This is very nice");
    }

    @SmallTest
    public void testChatFirstString() {
        String[] strHead = {
                "Hello? This is very nice", "Hello?",
                "This is cool! Really?", "This is cool!",
                "How.about.this? Cool", "How.about.this?"
        };

        for (int i=0; i<strHead.length; i+=2) {
            String head = q.getFirstSentence(strHead[i]);
            assertEquals("Chat.getFirstSentence", strHead[i+1], head);
        }

        String testString = "Hi! I'm a test string. Yep, that's all I am.";
        String testHead = q.getFirstSentence(testString);
        assertEquals(testHead, "Hi!");

        String testString2 = "This is a string with no seperators";
        String testHead2 = q.getFirstSentence(testString2);
        assertEquals(testHead2, "This is a string with no seperators");
    }

    @SmallTest
    public void testHead() {
        assertEquals("Head", "Hello?", q.getHead());
    }

    @SmallTest
    public void testequals()
    {
        Question question1 = new Question("Hello!");
        Question question2 = new Question("Hello!");
        String notQuestion = "";

        assertFalse(question1.equals(notQuestion));
        //assertTrue(question1.equals(question2));
        assertTrue(question1.equals(question2));
        assertTrue(question1.equals(question1));
        assertTrue(question2.equals(question2));
    }

    @SmallTest
    public void testcompareTo() throws InterruptedException {
        Question question1 = new Question("Hello!");
        Thread.sleep(5000);
        Question question2 = new Question("Hello too!");

        assertEquals(-1, question1.compareTo(question2));
        assertEquals(1, question2.compareTo(question1));
        assertEquals(0, question1.compareTo(question1));
    }
}
