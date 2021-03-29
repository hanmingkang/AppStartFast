package com.zz.appstartfast.task;

import android.content.Context;
import android.util.Log;

import com.zz.appstartfast.AppStartFast;

/**
 * @description:
 * @author: kangsx
 * @date: 3/28/21
 * @version: 1.1.0
 */
public class TaskRunnable implements Runnable {
    private Task task;
    private AppStartFast appStartFast;

    public TaskRunnable( AppStartFast appStartFast, Task task) {
        this.task = task;
        this.appStartFast = appStartFast;
    }

    @Override
    public void run() {
        Log.d("kang",task.getClass().getName() + "开始阻塞");
        task.lock();
        Log.d("kang",task.getClass().getName() + "结束阻塞");
        task.run(appStartFast.context);
        appStartFast.unLockDependencies(task);
    }
}
