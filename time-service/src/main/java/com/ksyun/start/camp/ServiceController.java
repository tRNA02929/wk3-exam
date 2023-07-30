package com.ksyun.start.camp;

import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.SimpleTimeServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServiceController {

    // hello
    @RequestMapping("/")
    public String index() {
        return "time-service";
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
        return RestResult.success()
                .descr("获取时间成功")
                .data(apiResponse);
    }
}
