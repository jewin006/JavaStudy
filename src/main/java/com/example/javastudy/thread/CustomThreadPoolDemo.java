package com.example.javastudy.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CustomThreadPoolDemo {
    public static void main(String[] args) {
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                2,                 // 核心线程数
                4,                 // 最大线程数
                60, TimeUnit.SECONDS,  // 空闲线程存活时间
                new ArrayBlockingQueue<>(2), // 有界任务队列
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy() // 拒绝策略：调用者执行
        );

        for (int i = 1; i <= 10; i++) {
            final int taskId = i;
            pool.execute(() -> {
                System.out.println("任务 " + taskId + " 开始执行，线程：" + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000); // 模拟业务逻辑
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                System.out.printf("任务 %d 执行完成，线程：%s%n", taskId, Thread.currentThread().getName());
            });
        }

        pool.shutdown();
    }
}
