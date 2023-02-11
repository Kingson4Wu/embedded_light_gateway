package com.kxw.light.gateway.tools.concurrent;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.kxw.light.gateway.tools.CommonUtils;
import io.netty.util.HashedWheelTimer;

public class ExecutionFrequencyUtils {


    private static volatile HashedWheelTimer timer;

    private static HashedWheelTimer getTimer() {

        if (Objects.nonNull(timer)) {
            return timer;
        }
        synchronized (ExecutionFrequencyUtils.class) {
            if (Objects.isNull(timer)) {

                ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ExecutionFrequencyUtils-%d").build();

                timer = new HashedWheelTimer(threadFactory, 1, TimeUnit.SECONDS, 60);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (Objects.nonNull(timer)) {
                        timer.stop();
                    }
                }));

            }
        }
        return timer;
    }

    public static void submitAsync(String taskName, List<Task> taskList, int executeNumPerSeconds) {

        List<List<Task>> subList = taskPartition(taskName, taskList, executeNumPerSeconds);
        int delaySeconds = 1;
        for (List<Task> list : subList) {

            int finalDelaySeconds = delaySeconds;

            list.stream().parallel().forEach(task -> {
                getTimer().newTimeout(timeout -> {
                    if (!isStop(taskName)) {
                        task.run();
                    }
                }, finalDelaySeconds, TimeUnit.SECONDS);
            });
            delaySeconds++;
        }
    }

    public static void submit(String taskName, List<Task> taskList, int executeNumPerSeconds) {

        List<List<Task>> subList = taskPartition(taskName, taskList, executeNumPerSeconds);
        for (List<Task> list : subList) {

            CommonUtils.sleep(1);

            list.stream().parallel().forEach(task -> {
                if (!isStop(taskName)) {
                    task.run();
                }
            });
        }
    }

    private static List<List<Task>> taskPartition(String taskName, List<Task> taskList, int executeNumPerSeconds) {
        int taskLimit = taskLimit(taskName);
        if (taskLimit == 0) {
            return Collections.emptyList();
        }

        if (isStop(taskName)) {
            return Collections.emptyList();
        }

        int executeNum = executeNumPerSeconds(taskName) > 0 ? executeNumPerSeconds(taskName) : executeNumPerSeconds;

        List<List<Task>> subList = taskLimit > 0 ?
                Lists.partition(taskList.subList(0, Math.min(taskList.size(), taskLimit)), executeNum) :
                Lists.partition(taskList, executeNum);
        return subList;
    }

    private static boolean isStop(String taskName) {
        return false;
    }

    private static int taskLimit(String taskName) {
        return -1;
    }

    private static int executeNumPerSeconds(String taskName) {
        return -1;
    }

}
