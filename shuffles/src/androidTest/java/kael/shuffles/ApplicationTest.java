package kael.shuffles;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }


    public void testShuffles() throws Exception {
        Shuffles<String> stringShuffles = new Shuffles<>();


        stringShuffles.setOnShufflesItemPositionChangeListener(new Shuffles.OnShufflesItemPositionChangeListener() {
            @Override
            public void OnShufflesItemPositionChange(OnShufflesItemPositionChangeArgs args) {
                Log.i("SHUFFLES", "SWAPPED: " + args.getFromPos() + " -> " + args.getToPos() + " @ " + args.getBeforeSwap().toString() + " -> " + args.getAfterSwap().toString());
            }
        });


        stringShuffles.add("a");
        stringShuffles.add("b");
        stringShuffles.add("c");
        stringShuffles.add("d");
        stringShuffles.add("e");
        stringShuffles.add("f");

        stringShuffles.roll();

        stringShuffles.roll();

    }
}