package com.zz.appstartfast.task;

import android.content.Context;
import android.util.Log;

import com.hanmingkang.appstartfast.task.Task;

import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: kangsx
 * @date: 3/26/21
 * @version: 1.1.0
 */
public class DTask extends Task {
    @Override
    public void run(Context context) {
        Log.d("kang123", "D初始化开始");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.d("kang123", "D初始化完成");
    }

    @Override
    public List<Class<? extends Task>> dependencies() {
        List<Class<? extends Task>> list = new ArrayList<>();
        list.add(ATask.class);
        list.add(BTask.class);
        list.add(CTask.class);
        return list;
    }
}
