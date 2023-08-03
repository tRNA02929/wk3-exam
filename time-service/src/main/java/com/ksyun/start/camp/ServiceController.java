package com.ksyun.start.camp;

import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.SimpleTimeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ServiceController {

    // hello
    @RequestMapping("/")
    public String index() {
        return "this is time-service";
    }

    // 在此实现简单时间服务的接口逻辑
    // 1. 调用 SimpleTimeService
    @GetMapping("/api/getDateTime")
    public Object getDateTime(String style) {
        String result = new SimpleTimeServiceImpl().getDateTime(style);
        ApiResponse apiResponse = ApiResponse.builder()
                .serviceId(ServiceAppRunner.serviceId)
                .result(result)
                .build();
//        log.info("getDateTime: {}", result);
        return RestResult.success().descr("获取时间成功").data(apiResponse);
    }
}
