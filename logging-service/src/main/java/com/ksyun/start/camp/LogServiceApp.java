package com.ksyun.start.camp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 基于 Spring Boot 实现的微服务
 * 日志收集服务
 */
@SpringBootApplication
public class LogServiceApp {

    public static void main(String[] args) {
        SpringApplication.run(LogServiceApp.class, args);
    }
}
