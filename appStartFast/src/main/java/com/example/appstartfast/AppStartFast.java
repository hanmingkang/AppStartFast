package com.example.appstartfast;

import android.content.Context;
import android.util.Log;


import com.example.appstartfast.task.Task;
import com.example.appstartfast.task.TaskRunnable;
import com.example.appstartfast.util.AppLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: kangsx
 * @date: 3/27/21
 * @version: 1.1.0
 */
public class AppStartFast {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 5));
    private static final int KEEP_ALIVE_SECOND = 5;
    /**
     * 存储有向无环图每个Task的入度
     */
    private Map<Task, Integer> taskHashMap;
    /**
     * Task任务列表
     */
    private List<Task> taskList;

    public ThreadPoolExecutor cpuThreadPool;
    public Context context;

    private static AppStartFast instance = null;

    public static AppStartFast getInstance(Builder builder) {
        if (instance == null) {
            synchronized (AppStartFast.class) {
                if (instance == null) {
                    instance = new AppStartFast(builder);
                }
            }
        }
        return instance;
    }

    private AppStartFast(Builder builder) {
        this.taskHashMap = builder.taskHashMap;
        this.taskList = builder.taskList;
        this.context = builder.context;
        if(builder.isEnable != null) {
            AppLog.isLogEnable = builder.isEnable;
        }
        cpuThreadPool = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                CORE_POOL_SIZE,
                KEEP_ALIVE_SECOND,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>());
    }


    public void start() {
        AppLog.D("start");
        if (taskList.size() == 0) {
            return;
        }
        for (Task task : taskList) {
            if (task.dependencies() != null) {
                taskHashMap.put(task, taskHashMap.get(task) + task.dependencies().size());
            }
        }
        //判断有向无环图是否存在环
        if (isCircle()) {
            throw new CircleException("tasks contains circle");
        }

        AppLog.D("taskList");

        for (final Task task : taskList) {
           AppLog.D(task.getClass().getName() + "进入线程池");
            cpuThreadPool.submit(new TaskRunnable(this, task));
        }
        Runtime.getRuntime().availableProcessors();
    }

    private boolean isCircle() {
        Queue<Task> queue = new LinkedList<>();
        for (Task task : taskHashMap.keySet()) {
            if (taskHashMap.get(task) == 0) {
                queue.offer(task);
            }
        }
        List<Task> orderTask = new ArrayList<>();
        while (!queue.isEmpty()) {
            Task taskTop = queue.poll();
            orderTask.add(taskTop);
            for (Task task : taskList) {
                if (task.dependencies() != null) {
                    for (Class<? extends Task> taskClazz : task.dependencies()) {
                        if (taskClazz == taskTop.getClass()) {
                            taskHashMap.put(task, taskHashMap.get(task) - 1);
                            if (taskHashMap.get(task) == 0) {
                                queue.offer(task);
                            }
                        }
                    }
                }
            }
        }
        //判断有环
        if (orderTask.size() < taskList.size()) {
            return true;
        }
        //排序后赋值给taskList
        taskList = orderTask;
        return false;
    }

    public void unLockDependencies(Task taskFinished) {
        AppLog.D("解除依赖");
        for (final Task task : taskList) {
            if (task.dependencies() != null) {
                for (Class<? extends Task> taskClazz : task.dependencies()) {
                    if (taskClazz == taskFinished.getClass()) {
                        AppLog.D(task.getClass().getName() + "解锁-1");
                        task.unLock();
                    }
                }
            }
        }
    }

    public static class Builder {
        private List<Task> taskList = new ArrayList<>();
        private Map<Task, Integer> taskHashMap = new HashMap<>();
        private Context context;
        private Boolean isEnable;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder addTask(Task task) {
            if (task != null) {
                if (!taskHashMap.containsKey(task)) {
                    taskHashMap.put(task, 0);
                    taskList.add(task);
                    task.initCountDownLatch();
                }
            }
            return this;
        }

        public Builder setLogEnable(Boolean isEnable) {
            this.isEnable = isEnable;
            return this;
        }

        public AppStartFast build() {
            return AppStartFast.getInstance(this);
        }
    }
}
