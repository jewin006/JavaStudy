package com.example.javastudy.thread.threadlocal;

import java.lang.ref.Reference;
import java.lang.reflect.Field;

public class ThreadLocalLeakDemo {

    public static void main(String[] args) throws Exception {
        // 启动一个线程来模拟使用 ThreadLocal 的过程
        Thread thread = new Thread(() -> {
            try {
                // ✅ 局部变量的 ThreadLocal，没有强引用持有
                ThreadLocal<Object> threadLocal = new ThreadLocal<>();
                threadLocal.set(new Object() {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println("Value is GC'ed");
                    }
                });

                // ✅ 打印设置后的 ThreadLocalMap
                System.out.println("Before GC:");
                printThreadLocalMap();

                // ❌ 主动清除 ThreadLocal 强引用
                threadLocal = null;

                // ✅ 手动触发 GC
                System.gc();
                Thread.sleep(1000); // 等待 GC

                // ✅ 再次打印 Map 状态
                System.out.println("After GC:");
                printThreadLocalMap();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
        thread.join();
    }

    // 打印当前线程的 ThreadLocalMap 的所有 entry
    private static void printThreadLocalMap() throws Exception {
        Thread thread = Thread.currentThread();

        // 获取 thread.threadLocals
        Field threadLocalsField = Thread.class.getDeclaredField("threadLocals");
        threadLocalsField.setAccessible(true);
        Object threadLocalMap = threadLocalsField.get(thread);

        if (threadLocalMap == null) {
            System.out.println("No ThreadLocalMap found.");
            return;
        }

        // 获取 ThreadLocalMap.Entry[] table
        Class<?> threadLocalMapClass = Class.forName("java.lang.ThreadLocal$ThreadLocalMap");
        Field tableField = threadLocalMapClass.getDeclaredField("table");
        tableField.setAccessible(true);
        Object[] table = (Object[]) tableField.get(threadLocalMap);

        for (Object entry : table) {
            if (entry != null) {
                // 获取 WeakReference(ThreadLocal) 的 referent
                Field referentField = Reference.class.getDeclaredField("referent");
                referentField.setAccessible(true);
                Object key = referentField.get(entry);

                Field valueField = entry.getClass().getDeclaredField("value");
                valueField.setAccessible(true);
                Object value = valueField.get(entry);

                System.out.println("Key: " + key + ", Value: " + value);
            }
        }
    }
}
