package com.example.javastudy.jvm;

/**
 * A. “带 default 方法”的接口会随实现类初始化而被初始化
 * @author zhangzhiwen30
 * @date 2025-08-12 16:28:31
 */

public class DemoA {
  static class Helper { static Object mark(String s){ System.out.println(s); return new Object(); } }

  interface PI {  // 父接口，含 default
    Object PI_INIT = Helper.mark(">> init PI");
    default void p() {}
  }
  interface CI extends PI { // 子接口，含 default
    Object CI_INIT = Helper.mark(">> init CI");
    default void c() {}
  }
  static class Impl implements CI {
    static Object IMPL_INIT = Helper.mark(">> init Impl");
  }

  public static void main(String[] args) {
    new Impl(); // 输出：PI -> CI -> Impl
  }
}
