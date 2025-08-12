package com.example.javastudy.jvm;

public class InterfaceInitDemo {
    static class Helper {
        static Object mark(String tag) {
            System.out.println(tag);
            return new Object();
        }
    }

    interface PI { // 父接口
        Object PI_INIT = Helper.mark(">> init PI");
        default void p() { System.out.println("PI.default p()"); }
    }

    interface CI extends PI { // 子接口
        Object CI_INIT = Helper.mark(">> init CI");
        default void c() { System.out.println("CI.default c()"); }
    }

    static class Impl implements CI { // 不重写 default 方法
        static Object IMPL_INIT = Helper.mark(">> init Impl");
        // 无需实现任何方法，继承 CI/PI 的 default 实现
    }

    static class ImplOverride implements CI {
        static Object IMPL2_INIT = Helper.mark(">> init ImplOverride");
        @Override public void c() { System.out.println("ImplOverride.c()"); } // 重写了 CI.default
        // 没有重写 PI.default p()
    }

    public static void main(String[] args) {
        System.out.println("=== Case A: 仅初始化实现类 ===");
        new Impl(); // 只会初始化 Impl，不会初始化 CI/PI
        /*
        结果：实现类初始化前，会先初始化接口，接口初始化顺序是从父接口到子接口。
        === Case A: 仅初始化实现类 ===
            >> init PI
            >> init CI
            >> init Impl
         */

    }
}
