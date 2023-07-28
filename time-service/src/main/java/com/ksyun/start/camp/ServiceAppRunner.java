package com.ksyun.start.camp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.ApplicationScope;

/**
 * 服务启动运行逻辑
 */
@Component
@EnableScheduling
public class ServiceAppRunner implements ApplicationRunner {

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 1. 向 registry 服务注册当前服务
        // TODO
        restTemplate.getForObject("http://localhost:8200/api/register", ApiResponse.class);
        System.out.println("服务启动运行逻辑:--->" + "向 registry 服务注册当前服务");
    }

    @Scheduled(cron = "*/3 * * * * ?")
    private void printNowDate() {
        // 2. 定期发送心跳逻辑
        // TODO
        restTemplate.getForObject("http://localhost:8200/api/heartbeat", ApiResponse.class);
        long nowDateTime = System.currentTimeMillis();
        System.out.println("固定定时任务执行:--->" + nowDateTime + "，此任务为每3秒执行一次");
    }
}
