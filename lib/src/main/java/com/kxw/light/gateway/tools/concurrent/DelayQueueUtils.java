package com.kxw.light.gateway.tools.concurrent;

import java.util.Objects;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import io.netty.util.HashedWheelTimer;

public class DelayQueueUtils {


    private volatile static HashedWheelTimer timer;

    private static HashedWheelTimer getTimer() {

        if (Objects.nonNull(timer)) {
            return timer;
        }
        synchronized (DelayQueueUtils.class) {
            if (Objects.isNull(timer)) {

                ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("DelayQueueUtils-%d").build();

                //ticksPerWheel，时间轮上一共有多少个 slot，默认 512 个。分配的 slot 越多，占用的内存空间就越大；
                //分配小的话，相同位置上的任务列表数越多，按实际任务数量合理分配
                //一圈下来有几格，默认512，而如果传入数值的不是2的N次方，则会调整为大于等于该参数的一个2的N次方数值，有利于优化hash值的计算
                timer = new HashedWheelTimer(threadFactory, 1, TimeUnit.SECONDS, 128);

                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    if (Objects.nonNull(timer)) {
                        timer.stop();
                    }
                }));

            }
        }
        return timer;
    }

    /**
     * 只适合执行时长较短的任务
     */
    public static void delay(Task task, long delaySeconds) {

        getTimer().newTimeout(timeout -> task.run(), delaySeconds, TimeUnit.SECONDS);
    }


}
