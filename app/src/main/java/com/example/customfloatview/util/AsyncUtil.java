package com.example.customfloatview.util;

import androidx.annotation.NonNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description:
 */
public class AsyncUtil {

  private static ExecutorService sFixedThreadPool = Executors.newFixedThreadPool(4);
  private static ExecutorService sCachedThreadPool = Executors.newCachedThreadPool();

  public static ExecutorService getFixedThreadPool() {
    return sFixedThreadPool;
  }

  public static ExecutorService getCachedThreadPool() {
    return sCachedThreadPool;
  }

  /**
   * 执行一个异步任务
   */
  public static void execute(@NonNull Runnable runnable){
    sFixedThreadPool.execute(runnable);
  }

}
