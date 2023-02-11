package com.kxw.light.gateway.tools.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Supplier;

import com.github.rholder.retry.RetryException;
import com.github.rholder.retry.Retryer;
import com.github.rholder.retry.RetryerBuilder;
import com.github.rholder.retry.StopStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * https://juejin.cn/post/7014099031718641694
 * https://cloud.tencent.com/developer/article/1752086
 */
public class RetryUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(RetryUtils.class);

    public static <R> R retryCall(String taskName, Function<R, Boolean> isFailure, int attemptTimes, Supplier<R> task) throws ExecutionException, RetryException {
        Retryer<R> retryer = RetryerBuilder.<R>newBuilder()
                .retryIfRuntimeException()
                .retryIfResult(result -> isFailure.apply(result))
                // 设置最大执行次数
                .withStopStrategy(StopStrategies.stopAfterAttempt(attemptTimes(taskName) > 0 ? attemptTimes(taskName) : attemptTimes))
                .build();
        return retryer.call(() -> task.get());

    }

    public static <R> R retryCall(String taskName, Function<R, Boolean> isFailure, int attemptTimes, Supplier<R> task, R defaultResult) {

        try {
            return retryCall(taskName, isFailure, attemptTimes, task);
        } catch (Exception e) {
            LOGGER.error("taskName:{},", taskName, e);
        }
        return defaultResult;
    }

    private static int attemptTimes(String taskName) {
        return -1;
    }
}
