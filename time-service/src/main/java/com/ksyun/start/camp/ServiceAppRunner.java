package com.ksyun.start.camp;

import com.ksyun.start.camp.rest.RestResult;
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

    static long serviceId;

    private String ipAddress = InetAddress.getLocalHost().getHostAddress();

    @Value("${server.port}")
    private int port;

    private boolean isRegister = false;

    HttpEntity<HashMap<String, Object>> httpEntity;

    public ServiceAppRunner() throws UnknownHostException {
    }

    static {
        serviceId = UUID.randomUUID().getMostSignificantBits();
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
                log.info("registry 服务未启动");
            }
        }
    }

    @Scheduled(cron = "*/3 * * * * ?")
    private void updateHeartbeat() {
        // 2. 定期发送心跳逻辑
        if (isRegister) {
            try {
                RestResult restResult = restTemplate.exchange("http://localhost:8180/api/heartbeat",
                        HttpMethod.POST, httpEntity, RestResult.class).getBody();
                log.info("registry 服务返回结果: {}", restResult.getDescr());
            } catch (Exception e) {
                log.info("registry 服务已下线");
                isRegister = false;
                run(null);
            }
        }
    }

    @Override
    public void destroy() throws Exception {
        // 3. 服务关闭时向 registry 发送取消注册请求
        try {
            RestResult restResult = restTemplate.exchange("http://localhost:8180/api/unregister",
                    HttpMethod.POST, httpEntity, RestResult.class).getBody();
            log.info("registry 服务返回结果: {}", restResult.getDescr());
        } catch (Exception e) {
            log.info("registry 服务已下线");
            isRegister = false;
        }
    }
}
