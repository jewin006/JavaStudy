package com.example.javastudy.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolDemo {
    public static void main(String[] args) {
        // 按需创建线程，空闲线程60s回收
        ExecutorService pool = Executors.newCachedThreadPool();

        for (int i = 1; i <= 6; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " 执行任务 " + taskId);
                try {
                    Thread.sleep(500); // 模拟业务耗时
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }

        pool.shutdown();
    }
}
