package com.kxw.light.gateway.tools.concurrent;

import java.time.Instant;

import com.kxw.light.gateway.tools.time.DateUtil;
import org.junit.Test;

public class DelayQueueUtilsTest {

    @Test
    public void delay() throws InterruptedException {

        System.out.println(DateUtil.formatTime(Instant.now()));

        DelayQueueUtils.delay(() -> {

            System.out.println("----1----" + DateUtil.formatTime(Instant.now()));

        }, 1);


        DelayQueueUtils.delay(() -> {

            System.out.println("----2----" + DateUtil.formatTime(Instant.now()));

        }, 1);

        DelayQueueUtils.delay(() -> {

            System.out.println("----3----" + DateUtil.formatTime(Instant.now()));

        }, 2);

        DelayQueueUtils.delay(() -> {

            System.out.println("----4----" + DateUtil.formatTime(Instant.now()));

        }, 2);

        DelayQueueUtils.delay(() -> {

            System.out.println("----5----" + DateUtil.formatTime(Instant.now()));

        }, 4);


        Thread.sleep(6000L);
    }

}
