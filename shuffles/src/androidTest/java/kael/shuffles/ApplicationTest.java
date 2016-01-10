package kael.shuffles;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testShuffles() throws Exception {
        Shuffles<String> stringShuffles = new Shuffles<>();
        stringShuffles.add("a");
        stringShuffles.add("b");
        stringShuffles.add("c");
        stringShuffles.add("d");
        stringShuffles.add("e");
        stringShuffles.add("f");

        stringShuffles.setOnItemPositionChangeListener(new Shuffles.OnShufflesItemPositionChangeListener() {
            @Override
            public void OnShufflesItemPositionChange(OnShufflesItemPositionChangeArgs args) {
                Log.i("testShuffles", "Got args: " + args.getBeforeSwap().get(0).toString() + "     <<<<---->>>>      " + args.getAfterSwap().get(0).toString());
            }
        });

        stringShuffles.insideRoll();

        List<String> randomStrings = stringShuffles.pick(3);

        randomStrings.size();

    }
}