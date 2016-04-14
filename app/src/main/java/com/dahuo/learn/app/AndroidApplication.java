package com.dahuo.learn.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author YanLu
 * @since 16/4/13
 */
public class AndroidApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);
    }


    public static RefWatcher getRefWatcher(Context context) {
        AndroidApplication application = (AndroidApplication) context.getApplicationContext();
      return application.refWatcher;
    }

    private RefWatcher refWatcher;
}
