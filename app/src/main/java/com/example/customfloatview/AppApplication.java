package com.example.customfloatview;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * @Author lili
 * @Date 2023/8/11-12:30
 */
public class AppApplication extends Application {
    private static AppApplication appApplication ;

    public static AppApplication getInstance() {
        return appApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appApplication = this;
        //注册监听Activity
        this.registerActivityLifecycleCallbacks(ActivityManager.getActivityManager());
    }

}
