package com.ksyun.start.camp.service;

import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 代表简单时间服务实现
 */
@Component
public class SimpleTimeServiceImpl implements SimpleTimeService {

    @Override
    public String getDateTime(String style) {

        // 开始编写简单时间服务的核心逻辑
        // 获取时间、格式化时间、返回
        ZoneId zoneId = ZoneId.of("GMT");
        if (style == null || style.equals("") || style.equals("full")) {
            return LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else if (style.equals("date")) {
            return LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else if (style.equals("time")) {
            return LocalDateTime.now(zoneId).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            return String.valueOf(Instant.now().toEpochMilli());
        }
    }
}
