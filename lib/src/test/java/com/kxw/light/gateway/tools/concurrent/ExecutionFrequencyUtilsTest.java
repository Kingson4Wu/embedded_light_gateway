package com.kxw.light.gateway.tools.concurrent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.kxw.light.gateway.tools.time.DateUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ExecutionFrequencyUtilsTest {

    @Before
    public void before() {

    }

    @Test
    public void submitAsync() {


    }


    @Test
    public void submit() {

        AtomicInteger total = new AtomicInteger();

        List<Task> taskList = new ArrayList<>(108);
        IntStream.range(0, 108).forEach(i -> {
            taskList.add(() -> {
                System.out.println(i + "----" + DateUtil.formatTime(Instant.now()));
                total.incrementAndGet();
            });
        });

        ExecutionFrequencyUtils.submit("sync", taskList, 35);

        System.out.println(total.get());

        Assert.assertEquals(total.get(), 108);


    }

}
