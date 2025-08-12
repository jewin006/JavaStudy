package com.example.javastudy.jvm;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 输出内容：给定 ClassLoader（含其父加载器）直接管理或可见的 JAR 与目录；JDK9+ 的引导层主要是模块（jrt:/），不再是 JAR。
 *
 * 无需 --add-opens，不访问私有字段；兼容 JDK8–JDK21。
 *
 * 若运行在 Tomcat / Spring Boot（LaunchedURLClassLoader） 等环境，通常也能拿到 URL，因为这些类加载器大多继承或包装 URLClassLoader。
 */
public class ClassLoaderJars {

    public static void main(String[] args) {
        // 1) 当前线程上下文加载器（通常是应用/框架想看的那个）。应用类加载器
        print("ContextClassLoader", Thread.currentThread().getContextClassLoader());

        // 2) 应用/系统类加载器（AppClassLoader）
        print("System(App)ClassLoader", ClassLoader.getSystemClassLoader());

        // 3) 平台类加载器（JDK9+），JDK8 返回 null（忽略即可）
        try {
            ClassLoader platform = (ClassLoader) ClassLoader.class
                    .getMethod("getPlatformClassLoader").invoke(null);
            print("PlatformClassLoader (JDK9+)", platform);
        } catch (ReflectiveOperationException ignore) {}

        // 4) 引导类加载器（Bootstrap）
        printBootstrap();
    }

    /** 打印给定 ClassLoader 及其父加载器可见的 URL（JAR/目录） */
    static void print(String title, ClassLoader cl) {
        System.out.println("\n=== " + title + " ===");
        int level = 0;
        for (ClassLoader x = cl; x != null; x = x.getParent(), level++) {
            System.out.println("[" + level + "] " + x.getClass().getName());
            for (URL u : urlsOf(x)) {
                System.out.println("  ==> " + u.toExternalForm());
            }
        }
    }

    /** 尽量用公开 API 获取 URL；不是 URLClassLoader 时降级到 java.class.path（仅对 AppCL 有意义） */
    static List<URL> urlsOf(ClassLoader cl) {
        List<URL> out = new ArrayList<>();
        if (cl instanceof URLClassLoader) {
            out.addAll(Arrays.asList(((URLClassLoader) cl).getURLs()));
        } else if (cl == ClassLoader.getSystemClassLoader()) {
            // JDK9+ AppClassLoader 仍是 URLClassLoader，但为稳妥做个降级
            String cp = System.getProperty("java.class.path", "");
            for (String e : cp.split(File.pathSeparator)) {
                if (e.isEmpty()) continue;
                Path p = Paths.get(e);
                try {
                    URI uri = p.toUri();
                    out.add(uri.toURL());
                } catch (Exception ignore) {}
            }
        }
        return dedup(out);
    }

    /** JDK8 的引导路径来自 sun.boot.class.path；JDK9+ 主要来自模块，不再是 JAR（给出模块清单） */
    static void printBootstrap() {
        System.out.println("\n=== Bootstrap (引导) ===");
        String sbcp = System.getProperty("sun.boot.class.path"); // JDK8 有，JDK9+ 无
        if (sbcp != null) {
            for (String e : sbcp.split(File.pathSeparator)) {
                if (!e.isEmpty()) System.out.println("  ==> " + Paths.get(e).toUri());
            }
            return;
        }
        // JDK9+：引导绝大部分在模块镜像 jrt:/，不再是 JAR；列出已解析的启动层模块
//        try {
//            Set<String> mods = ModuleLayer.boot().modules()
//                    .stream().map(m -> m.getName()).collect(Collectors.toCollection(TreeSet::new));
//            for (String m : mods) System.out.println("  ==> jrt:/" + m);
//        } catch (Throwable t) {
//            System.out.println("  (No bootstrap class path; running on modules)");
//        }
    }

    static List<URL> dedup(List<URL> urls) {
        // 去重且保持顺序
        LinkedHashSet<URL> set = new LinkedHashSet<>(urls);
        return new ArrayList<>(set);
    }
}
