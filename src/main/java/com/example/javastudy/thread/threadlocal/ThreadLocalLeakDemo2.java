package com.example.javastudy.thread.threadlocal;// ThreadLocalLeakDemo2.java

import java.lang.ref.Reference;
import java.lang.reflect.Field;

public class ThreadLocalLeakDemo2 {

    // ✅ 强引用持有 ThreadLocal 实例
    private static final ThreadLocal<Object> SAFE_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) throws Exception {
        Thread t = new Thread(() -> {
            SAFE_LOCAL.set(new Object() {
                @Override
                protected void finalize() throws Throwable {
                    System.out.println("✅ Value from SAFE_LOCAL GC'ed");
                }
            });

            System.gc();
            try {
                Thread.sleep(1000);
                printThreadLocalMap("✅ static final ThreadLocal 演示");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        t.start();
        t.join();
    }

    private static void printThreadLocalMap(String tag) throws Exception {
        Thread thread = Thread.currentThread();
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object threadLocalMap = threadLocalsField.get(thread);
        if (threadLocalMap == null) {
            System.out.println(tag + " ➜ 无 ThreadLocalMap");
            return;
        }

        Class<?> threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
        Field tableField = threadLocalMapClass.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(threadLocalMap);

        System.out.println(tag + " ➜ 打印 ThreadLocalMap");
        for (Object entry : table) {
            if (entry != null) {
                Field valueField = entry.getClass().getDeclaredField("value");
                valueField.setAccessible(true);
                Object value = valueField.get(entry);

                Field referentField = Reference.class.getDeclaredField("referent");
                referentField.setAccessible(true);
                Object key = referentField.get(entry);

                System.out.printf("Key: %s, Value: %s%n", key, value);
            }
        }
    }
}
