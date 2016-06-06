package com.dahuo.learn.agera;

import android.util.Log;

import com.google.android.agera.BaseObservable;

/**
 * @author YanLu
 * @since 16/4/24
 */
public class SimpleObservable extends BaseObservable{
    private static final String TAG = "SimpleObservable";

    public SimpleObservable() {
    }

    public void update() {
        dispatchUpdate();
    }

    @Override
    protected void observableActivated() {
        Log.d(TAG, "observableActivated");
    }

    @Override
    protected void observableDeactivated() {
        Log.d(TAG, "observableDeactivated");
    }


}
