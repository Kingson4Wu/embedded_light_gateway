package com.kxw.light.gateway.tools.concurrent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

public class ParallelTaskTest {


   /* @Rule
    public ContiPerfRule contiPerfRule = new ContiPerfRule();

    //最终的测试报告位于target/contiperf-report/index.html，使用浏览器打开即可。
    @Test
    @PerfTest(invocations = 10000, threads = 100)//100个线程 执行10000次
    @Required(throughput = 20, average = 50, totalTime = 5000, percentile99 = 10000)
//：要求每秒至少执行20个测试；要求平均执行时间不超过50ms；要求总的执行时间不超过5s；要求99%的测试不超过10s;
    public void test() {
        StringBuffer sb = new StringBuffer();
        ParallelTask.newTask()
                .addTask(() -> sb.append("f"))
                .addTask(() -> sb.append("g"))
                .addTask(() -> sb.append("v")).execute();
        Assert.assertEquals(sb.length(), 3);
    }*/


    @Test
    public void executor() throws ExecutionException, InterruptedException {
        ThreadFactory guavaThreadFactory = new ThreadFactoryBuilder().setNameFormat("rire-%d").build();

        ExecutorService executor = new ThreadPoolExecutor(4, 2 * 2,
                60, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1024), guavaThreadFactory, (r, e) -> {
            throw new RejectedExecutionException(r.toString());
        });

        StringBuffer sb = new StringBuffer();
        ParallelTask.newTask()
                .addTask(() -> {
                    sb.append("f");
                    System.out.println(Thread.currentThread().getName());
                })
                .addTask(() -> {
                    sb.append("b");
                    System.out.println(Thread.currentThread().getName());
                })
                .addTask(() -> {
                    sb.append("n");
                    System.out.println(Thread.currentThread().getName());
                }).execute(executor);
        Assert.assertEquals(sb.length(), 3);

    }

    /*@Test
    public void execute() {

        FileUtils.deleteQuietly(new File("work" + File.separator + "ParallelTaskTest.txt"));

        IntStream.range(0, 100).parallel().forEach(i -> {

            StringBuffer sb = new StringBuffer();

            ParallelTask parallelTask =
                    ParallelTask.newTask()
                            .addTask(() -> {
                                try {
                                    Thread.sleep(100L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sb.append("f");
                                System.out.println(1);
                            })
                            .addTask(() -> {
                                try {
                                    Thread.sleep(100L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sb.append("g");
                                System.out.println(2);
                            })
                            .addTask(() -> {
                                try {
                                    Thread.sleep(100L);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                sb.append("v");
                                System.out.println(3);
                            });

            parallelTask.execute();

            System.out.println(i + "-------" + sb.length() + "----" + sb);
            try {
                FileUtils.writeStringToFile(new File("work" + File.separator + "ParallelTaskTest.txt"), i + "-------" + sb.length() + "----" + sb + "===" + parallelTask.taskSize() + "====" + "\n", true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Assert.assertEquals(sb.length(), 3);
        });


    }*/

}
