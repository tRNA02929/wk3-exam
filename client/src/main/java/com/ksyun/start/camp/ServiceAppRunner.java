package com.ksyun.start.camp;

import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.TimeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.UUID;

/**
 * 服务启动运行逻辑
 */
@Slf4j
@Component
@EnableScheduling
public class ServiceAppRunner implements ApplicationRunner, DisposableBean {

    private RestTemplate restTemplate = new RestTemplate();

    @Value("${spring.application.name}")
    private String serviceName;

    public static String serviceId;

    private String ipAddress = InetAddress.getLocalHost().getHostAddress();

    @Value("${server.port}")
    private int port;

    private boolean isRegister = false;
    private boolean isLogServiceOn = false;

    HttpEntity<HashMap<String, Object>> httpEntity;

    LinkedHashMap<String, Object> logMap;

    public ServiceAppRunner() throws UnknownHostException {
    }

    static {
        serviceId = String.valueOf(UUID.randomUUID().getMostSignificantBits());
    }

    private void initialHttpEntity() {
        HashMap<String, Object> forObject = new HashMap<>();
        forObject.put("serviceName", serviceName);
        forObject.put("serviceId", serviceId);
        forObject.put("ipAddress", ipAddress);
        forObject.put("port", port);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpEntity = new HttpEntity<>(forObject, httpHeaders);

        logMap = new LinkedHashMap<>();
        logMap.put("serviceName", serviceName);
        logMap.put("serviceId", serviceId);
        logMap.put("datetime", null);
        logMap.put("level", "info");
        logMap.put("message", "Client status is OK");
    }

    @Override
    public void run(ApplicationArguments args) {
        // 1. 向 registry 服务注册当前服务
        initialHttpEntity();
        while (!isRegister) {
            try {
                RestResult restResult = restTemplate.exchange("http://localhost:8180/api/register",
                        HttpMethod.POST, httpEntity, RestResult.class).getBody();
                log.info("registry 服务返回结果: {}", restResult.getDescr());
                isRegister = true;
            } catch (Exception e) {
                log.error("registry 服务未启动");
            }
        }
    }

    @Scheduled(cron = "*/50 * * * * ?")
    private void updateHeartbeat() {
        // 2. 定期发送心跳逻辑
        if (isRegister) {
            try {
                RestResult restResult = restTemplate.exchange("http://localhost:8180/api/heartbeat",
                        HttpMethod.POST, httpEntity, RestResult.class).getBody();
                log.info("registry 服务返回结果: {}", restResult.getDescr());
            } catch (Exception e) {
                log.error("registry 服务已下线");
                isRegister = false;
                run(null);
            }
        }
    }

    @Scheduled(cron = "*/1 * * * * ?")
    private void logging() {
        // 3. 定期写日志
        if (!isLogServiceOn) {
            try {
                String s = restTemplate.getForObject("http://localhost:8320/", String.class);
                isLogServiceOn = true;
            } catch (Exception e) {
                log.warn("日志写入失败, 日志服务不可用");
                return;
            }
        }
        String datetime = new TimeServiceImpl().getDateTime("unix");
        if (datetime == null) {
            log.warn("日志写入失败, 时间服务不可用");
            return;
        }
        try {
            logMap.put("datetime", datetime);
            restTemplate.postForEntity("http://localhost:8320/api/logging", logMap, Object.class);
        } catch (Exception e) {
            log.warn("日志写入失败, 日志服务不可用");
            isLogServiceOn = false;
        }
    }

    @Override
    public void destroy() throws Exception {
        // 4. 服务关闭时向 registry 发送取消注册请求
        try {
            RestResult restResult = restTemplate.exchange("http://localhost:8180/api/unregister",
                    HttpMethod.POST, httpEntity, RestResult.class).getBody();
            log.info("registry 服务返回结果: {}", restResult.getDescr());
        } catch (Exception e) {
            log.warn("registry 服务已下线");
            isRegister = false;
        }
    }
}
