package com.ksyun.start.camp.service;

import com.ksyun.start.camp.rest.RestResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 代表远端时间服务接口实现
 */
@Component
public class TimeServiceImpl implements TimeService {

    @Override
    public String getDateTime(String style) {

        // 开始编写你的逻辑，下面是提示
        // 1. 连接到 registry 服务，获取远端服务列表
        // 2. 从远端服务列表中获取一个服务实例
        // 3. 执行远程调用，获取指定格式的时间
        // 4. 时区为 UTC+8
        // TODO
        RestTemplate restTemplate = new RestTemplate();
        RestResult<List<Map<String, Object>>> restResult = restTemplate.getForObject("http://localhost:8180/api/discovery/?serviceName=time-service", RestResult.class);
        if (restResult.getCode() != 200) {
            return null;
        }
        Map<String, Object> map = restResult.getData().get(0);
        RestResult<Map<String, Object>> result = restTemplate.getForObject("http://" +
                map.getOrDefault("ipAddress", "") + ":" +
                map.getOrDefault("port", "") +
                "/api/getDateTime?style=" + style, RestResult.class);
        if (result.getCode() != 200) {
            return null;
        }
        ZoneId zoneId = ZoneId.of("UTC+8");
        Instant instant = Instant.ofEpochMilli(Long.parseLong((String) result.getData().get("result")));
        ZonedDateTime zonedDateTime = instant.atZone(zoneId);
        return zonedDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS"));
//        return null;
    }
}
