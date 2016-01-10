package kael.shuffles;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yonatan on 09/01/16.
 */
public class Shuffles<T> extends ArrayList<T> {

    public interface OnShufflesItemPositionChangeListener {
        class OnShufflesItemPositionChangeArgs {
            private List<?> beforeSwap;
            private List<?> afterSwap;

            public List<?> getBeforeSwap() {
                return beforeSwap;
            }

            public List<?> getAfterSwap() {
                return afterSwap;
            }

        }

        void OnShufflesItemPositionChange(OnShufflesItemPositionChangeArgs args);
    }

    private OnShufflesItemPositionChangeListener mOnShufflesItemPositionChangeListener = null;

    public Shuffles() {

    }

    public Shuffles(T... objects) {
        for (T object : objects) {
            add(object);
        }
    }

    public Shuffles(List<T> objects) {
        for (T object : objects) {
            add(object);
        }
    }

    public void setOnItemPositionChangeListener(@Nullable OnShufflesItemPositionChangeListener listener) {
        mOnShufflesItemPositionChangeListener = listener;
    }

    public Shuffles<T> roll(int times) throws IllegalArgumentException {
        return roller(new Shuffles<T>(this), times);
    }

    public Shuffles<T> roll() {
        return roll(1);
    }

    public void insideRoll(int times) throws IllegalArgumentException {
        roller(this, times);
    }

    public void insideRoll() {
        insideRoll(1);
    }

    public T pick() {
        return get(new Random().nextInt(size()));
    }

    public List<T> pick(int amount) throws ArrayIndexOutOfBoundsException {
        if (amount > size()) {
            throw new ArrayIndexOutOfBoundsException("Requested " + amount + " items but only have " + size() + ".");
        }

        if (amount < 1) {
            throw new ArrayIndexOutOfBoundsException("You cant request to pick less than one items.");
        }

        List<T> resultList = new ArrayList<>();
        while (resultList.size() < amount) {
            resultList.add(pick());
        }

        return resultList;
    }

    @Nullable
    private Shuffles<T> roller(@NonNull Shuffles<T> objects, int times) throws IllegalArgumentException {
        if (objects.size() < 2) {
            Log.w("Shuffles", "Not enough objects to roll, must be more than 1. Nothing happend.");
            return objects;
        }

        if (times < 1) {
            throw new IllegalArgumentException("Can't roll up less than 1 times.");
        }

        OnShufflesItemPositionChangeListener.OnShufflesItemPositionChangeArgs changeArgs = new OnShufflesItemPositionChangeListener.OnShufflesItemPositionChangeArgs();

        changeArgs.beforeSwap = new Shuffles<T>(objects);

        for (int i = 0; i < times; i++) {
            Random random = new Random();
            int from = random.nextInt(objects.size());
            int to = random.nextInt(objects.size());
            T temp;

            // fill the temp with the dest object
            temp = objects.get(to);

            // swap the items
            objects.set(to, objects.get(from));
            objects.set(from, temp);

            // set the structure after swap to this
            changeArgs.afterSwap = (objects);

            if (mOnShufflesItemPositionChangeListener != null) {
                // cast signal to OnShufflesItemPositionChangeListener
                mOnShufflesItemPositionChangeListener.OnShufflesItemPositionChange(changeArgs);
            }
        }

        return objects;
    }
}
