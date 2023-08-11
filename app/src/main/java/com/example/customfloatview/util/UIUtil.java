package com.example.customfloatview.util;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * description:
 */
public class UIUtil {

  private static final Handler UI_HANDLER = new Handler(Looper.getMainLooper());

  public static void runOnUIThread(@NonNull Runnable runnable) {
    UI_HANDLER.post(runnable);
  }

  public static void runOnUIThreadDelay(@NonNull Runnable runnable, long delayMillis) {
    UI_HANDLER.postDelayed(runnable, delayMillis);
  }

  // 获得屏幕宽度
  public static int getScreenWidthPx(Context context) {
    return context.getResources().getDisplayMetrics().widthPixels;
  }

  // 获得屏幕高度
  public static int getScreenHeightPx(Context context) {
    return context.getResources().getDisplayMetrics().heightPixels;
  }

  /**
   * 根据context获得activity
   */
  @Nullable
  public static Activity getActivity(@Nullable Context context) {
    while (context instanceof ContextWrapper) {
      if (context instanceof Activity) {
        return (Activity) context;
      }
      context = ((ContextWrapper) context).getBaseContext();
    }
    return null;
  }

}
