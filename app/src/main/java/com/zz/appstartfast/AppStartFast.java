package com.zz.appstartfast;

import android.content.Context;

import com.zz.appstartfast.task.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description:
 * @author: kangsx
 * @date: 3/27/21
 * @version: 1.1.0
 */
public class AppStartFast {
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(2, Math.min(CPU_COUNT - 1, 4));
    /**
     * 当前任务队列，存储着相互依赖的关系
     */
    private List<List<Task>> listTaskList = new ArrayList<>();
    /**
     * 空闲任务队列，存储相互依赖的关系，在cpu空闲时执行
     */
    private List<List<Task>> listIdleTaskList= new ArrayList<>();

    public ExecutorService executorService;
    private Context context;

    private AppStartFast(Builder builder) {
        executorService = Executors.newFixedThreadPool(CORE_POOL_SIZE);
        this.listTaskList = builder.listTaskList;
        this.context = builder.context;
    }

    private void start() {
        if (listTaskList.size() == 0) {
            return;
        }
        for (final List<Task> tasks : listTaskList) {
            if (tasks.size() == 1) {
                executorService.submit(new Runnable() {
                    @Override
                    public void run() {
                        tasks.get(0).run(context);
                    }
                });
            }
        }
    }


    class Builder {
        private List<List<Task>> listTaskList = new ArrayList<>();
        private Context context;

        Builder(Context context) {
            this.context = context;
        }

        public Builder addTask(Task task) {
            List<Task> tasks;
            if (task.dependencies() == null) {
                tasks = new ArrayList<>();
            } else {
                tasks = task.dependencies();
            }
            tasks.add(task);
            listTaskList.add(tasks);
            return this;
        }

        public Builder addIdleTask(Task task){

        }

        public AppStartFast build() {
            return new AppStartFast(this);
        }
    }


}
