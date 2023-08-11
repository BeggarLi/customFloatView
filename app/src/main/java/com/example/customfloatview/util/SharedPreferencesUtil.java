package com.example.customfloatview.util;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.example.customfloatview.AppApplication;



/**
 * sp工具类
 */
public class SharedPreferencesUtil {

    // 空字符串
    private static final String EMPTY_STR = "";
    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    private static final SharedPreferences mSPrefs =
            AppApplication.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);


    public static void putString(String key, String value) {
        SharedPreferences.Editor edit = mSPrefs.edit();
        edit.putString(key, value);
        edit.apply();
    }
    public static void putBoolean(String key, boolean value) {
        mSPrefs.edit().putBoolean(key, value).apply();
    }

//    public static  <T> T get(@NonNull String key, @NonNull Type type) {
//        String value = mSPrefs.getString(key, EMPTY_STR);
//        if (value == null || value.equals(EMPTY_STR)) {
//            return null;
//        }
//        return (T) value;
//    }

    public static String getString(@NonNull String key,@NonNull String defaultValue){
        String value = mSPrefs.getString(key, defaultValue);
        if (value == null ) {
            return defaultValue;
        }
        return value;
    }

    public static boolean getBoolean(@NonNull String key, boolean defaultValue) {
        Boolean value = mSPrefs.getBoolean(key,defaultValue);
        if (value == null) {
            return defaultValue;
        }
        return value;
    }


}
