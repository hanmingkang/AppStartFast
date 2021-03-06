package com.zz.appstartfast.task;

import android.content.Context;
import android.util.Log;

import com.hanmingkang.appstartfast.task.Task;

import java.util.List;

/**
 * @description:
 * @author: kangsx
 * @date: 3/26/21
 * @version: 1.1.0
 */
public class FTask extends Task {

    @Override
    public void run(Context context) {
        Log.d("kang123", "F初始化开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("kang123", "F初始化完成");
    }

    @Override
    public List<Class<? extends Task>> dependencies() {
        return null;
    }
}
