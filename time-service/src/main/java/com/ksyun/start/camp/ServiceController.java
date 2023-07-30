package com.ksyun.start.camp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/api")
public class ServiceController {

    // 在此实现简单时间服务的接口逻辑
    // 1. 调用 SimpleTimeService
    @GetMapping("/api/getDateTime")
    public Object getDateTime(String style) {
        if (style == null || style.equals(""))
            return "invalid param";
        if (style.equals("full")) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (style.equals("date")) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (style.equals("time")) {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else if (style.equals("unix")) {
            return Instant.now().toEpochMilli();
        } else {
            return "invalid param";
        }
    }
}
