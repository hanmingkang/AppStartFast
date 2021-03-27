package com.zz.appstartfast.task;

import android.content.Context;

import java.util.List;

/**
 * @description:
 * @author: kangsx
 * @date: 3/26/21
 * @version: 1.1.0
 */
public interface Task {
    void run(Context context);

    List<Task> dependencies();
}
