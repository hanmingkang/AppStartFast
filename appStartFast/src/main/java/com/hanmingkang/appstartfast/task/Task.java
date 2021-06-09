package com.hanmingkang.appstartfast.task;

import android.content.Context;

import com.hanmingkang.appstartfast.util.AppLog;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: kangsx
 * @date: 3/26/21
 * @version: 1.1.0
 */
public abstract class Task {
    private CountDownLatch countDownLatch;

    public abstract void run(Context context);

    public abstract List<Class<? extends Task>> dependencies();

    public void initCountDownLatch() {
        if (dependencies() != null && dependencies().size() != 0) {
            countDownLatch = new CountDownLatch(dependencies().size());
        }
    }

    final public void lock() {
        if (countDownLatch != null) {
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    final public void unLock() {
        AppLog.D(this.getClass().getName() + "--判空");
        if (countDownLatch != null) {
           AppLog.D(this.getClass().getName() + "--");
            countDownLatch.countDown();
        }
    }
}
