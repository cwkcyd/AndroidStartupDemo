package com.dahuo.learn.app;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * @author YanLu
 * @since 16/4/13
 */
public class AndroidApp extends Application{
    private static AndroidApp instance;
    public static String cacheDir = "";

    public static AndroidApp getInstance() {
        return instance;
    }

    public static Context getAppContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        refWatcher = LeakCanary.install(this);

        /**
         * 如果存在SD卡则将缓存写入SD卡,否则写入手机内存
         */
        if (getApplicationContext().getExternalCacheDir() != null && ExistSDCard()) {
            cacheDir = getApplicationContext().getExternalCacheDir().toString();
        } else {
            cacheDir = getApplicationContext().getCacheDir().toString();
        }
    }

    private boolean ExistSDCard() {
        return android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
    }

    public static RefWatcher getRefWatcher(Context context) {
        AndroidApp application = (AndroidApp) context.getApplicationContext();
      return application.refWatcher;
    }

    private RefWatcher refWatcher;
}
