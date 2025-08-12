package com.example.javastudy.jvm;

/**
 * B. “不含 default 方法”的接口不会因实现类初始化而被初始化
 *
 * @author zhangzhiwen30
 * @date 2025-08-12 16:29:26
 */

public class DemoB {
  static class Helper { static Object mark(String s){ System.out.println(s); return new Object(); } }

  interface J {                // 无 default 方法
    Object J_INIT = Helper.mark(">> init J"); // 非常量静态字段
  }
  static class Impl implements J {
    static Object IMPL_INIT = Helper.mark(">> init Impl");
  }

  public static void main(String[] args) {
    new Impl();        // 只打印：>> init Impl
    System.out.println(J.J_INIT); // 到这里才初始化接口 J，打印：>> init J
  }
}
