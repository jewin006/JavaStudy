package com.example.javastudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaStudyApplication {
    public static void main(String[] args) {
        SpringApplication.run(JavaStudyApplication.class, args);
        System.out.printf("Java Study Application has started successfully!%n");
        int num = 42; // 存在 main 线程的栈里
        System.out.println("________");
        System.out.println(3 & 1); // 输出: 1
        System.out.println(6 & 1);
    }

}

