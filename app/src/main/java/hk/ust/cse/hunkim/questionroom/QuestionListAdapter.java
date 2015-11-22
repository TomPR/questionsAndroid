package hk.ust.cse.hunkim.questionroom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.text.Html;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.client.Query;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import hk.ust.cse.hunkim.questionroom.db.DBUtil;
import hk.ust.cse.hunkim.questionroom.question.Question;

/**
 * @author greg
 * @since 6/21/13
 * <p/>
 * This class is an example of how to use FirebaseListAdapter. It uses the <code>Chat</code> class to encapsulate the
 * data for each individual chat message
 */


public class QuestionListAdapter extends FirebaseListAdapter<Question> {

    // The mUsername for this client. We use this to indicate which messages originated from this user
    private String roomName;
    MainActivity activity;

    public QuestionListAdapter(Query ref, Activity activity, int layout, String roomName) {
        super(ref, Question.class, layout, activity);

        // Must be MainActivity
        assert (activity instanceof MainActivity);

        this.activity = (MainActivity) activity;
    }



    /**
     * Bind an instance of the <code>Chat</code> class to our view. This method is called by <code>FirebaseListAdapter</code>
     * when there is a data change, and we are given an instance of a View that corresponds to the layout that we passed
     * to the constructor, as well as a single <code>Chat</code> instance that represents the current data to bind.
     *
     * @param view     A view instance corresponding to the layout we passed to the constructor.
     * @param question An instance representing the current state of a chat message
     */
    @Override
    protected void populateView(View view, Question question) {
        DBUtil dbUtil = activity.getDbutil();

        // Map a Chat object to an entry in our listview
        int echo = question.getEcho();
        Button echoButton = (Button) view.findViewById(R.id.echo);
        echoButton.setText("" + echo);
        echoButton.setTextColor(Color.BLUE);


        echoButton.setTag(question.getKey()); // Set tag for button

        echoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MainActivity m = (MainActivity) view.getContext();
                        m.updateEcho((String) view.getTag());
                    }
                }

        );

        String msgString = "";

        question.updateNewQuestion();
        if (question.isNewQuestion()) {
            msgString += "<font color=red>NEW </font>";
        }

        msgString += "<B>" + question.getHead() + "</B>" + question.getDesc() + "<br />" + "Posted " + getTimeAgo(question.getTimestamp()) ;

        ((TextView) view.findViewById(R.id.head_desc)).setText(Html.fromHtml(msgString));
        view.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        MainActivity m = (MainActivity) view.getContext();
                                        m.updateEcho((String) view.getTag());
                                    }
                                }

        );

        // check if we already clicked
        boolean clickable = !dbUtil.contains(question.getKey());

        echoButton.setClickable(clickable);
        echoButton.setEnabled(clickable);
        view.setClickable(clickable);


        // http://stackoverflow.com/questions/8743120/how-to-grey-out-a-button
        // grey out our button
        if (clickable) {
            echoButton.getBackground().setColorFilter(null);
        } else {
            echoButton.getBackground().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        }


        view.setTag(question.getKey());  // store key in the view
    }

    @Override
    protected void sortModels(List<Question> mModels) {
        Collections.sort(mModels);
    }

    @Override
    protected void setKey(String key, Question model) {
        model.setKey(key);
    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;
    }

    private Long getAgo(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH) ;
        long nowTime = cal.getTimeInMillis();
        long ago = nowTime - time ;
        return ago ;
    }

    private static final int SECOND_MILLIS = 1000;
    private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
    private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
    private static final int DAY_MILLIS = 24 * HOUR_MILLIS;


    public static String getTimeAgo(long time) {
        if (time < 1000000000000L) {
            // if timestamp given in seconds, convert to millis
            time *= 1000;
        }

        Calendar cal = Calendar.getInstance(Locale.ENGLISH) ;
        long nowTime = cal.getTimeInMillis() ;

        final long diff = nowTime - time;
        if (diff < MINUTE_MILLIS) {
            return "just now";
        } else if (diff < 2 * MINUTE_MILLIS) {
            return "a minute ago";
        } else if (diff < 50 * MINUTE_MILLIS) {
            return diff / MINUTE_MILLIS + " minutes ago";
        } else if (diff < 90 * MINUTE_MILLIS) {
            return "an hour ago";
        } else if (diff < 24 * HOUR_MILLIS) {
            return diff / HOUR_MILLIS + " hours ago";
        } else if (diff < 48 * HOUR_MILLIS) {
            return "yesterday";
        } else {
            return diff / DAY_MILLIS + " days ago";
        }
    }
}
