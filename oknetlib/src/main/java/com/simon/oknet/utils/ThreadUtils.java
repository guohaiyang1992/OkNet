package com.simon.oknet.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * description: 线程工具库
 * author: Simon
 * created at 2017/9/1 下午3:21
 */

public class ThreadUtils {
    private static final Handler handler = new Handler(Looper.getMainLooper());
    private static final ExecutorService cacheThreadPool = Executors.newCachedThreadPool();
    private static final int NO_DELAY = 0;

    /**
     * 执行在主线程
     * support delay
     *
     * @param runnable --runnable
     * @param delay    --延迟时间 unit:ms
     */
    public static void runInMain(Runnable runnable, long delay) {
        if (handler != null && runnable != null && delay >= 0) {
            handler.postDelayed(runnable, delay);
        }
    }

    /**
     * 执行在主线程
     *
     * @param runnable --runnable
     */
    public static void runInMain(Runnable runnable) {
        runInMain(runnable, 0);
    }

    /**
     * excute in backthread
     * support delay
     *
     * @param runnable -- runnable
     * @param delay    --delay time unit:ms
     */
    public static void runInBack(final Runnable runnable, final long delay) {
        cacheThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                //--delay--
                if (delay > NO_DELAY) { //小于或等于0 不sleep
                    try {
                        Thread.sleep(delay);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //--run--
                runnable.run();
            }
        });
    }

    /**
     * excute in backthread
     *
     * @param runnable -- runnable
     */
    public static void runInBack(Runnable runnable) {
        runInBack(runnable, NO_DELAY);
    }
}
