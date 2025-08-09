package com.example.javastudy.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolDemo {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // 1. 延迟执行一次
        scheduler.schedule(() -> 
            System.out.println("延迟任务执行: " + System.currentTimeMillis()),
            2, TimeUnit.SECONDS);

        // 2. 固定周期执行（任务执行完成后，隔固定时间再执行）
        scheduler.scheduleAtFixedRate(() -> 
            System.out.println("固定速率任务执行: " + System.currentTimeMillis()),
            1, 3, TimeUnit.SECONDS);

        // 3. 固定延迟执行（任务结束后，延迟固定时间再执行）
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("固定延迟任务执行: " + System.currentTimeMillis());
            try {
                Thread.sleep(2000); // 模拟任务耗时
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }, 1, 3, TimeUnit.SECONDS);
    }
}
