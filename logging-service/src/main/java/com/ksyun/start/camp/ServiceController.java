package com.ksyun.start.camp;

import com.ksyun.start.camp.entity.LogEntity;
import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.LoggingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 实现日志服务 API
 */
@RestController
public class ServiceController {

    @Autowired
    private LoggingServiceImpl loggingService;

    // TODO: 实现日志服务 API
    @RequestMapping("/")
    public String index() {
        return "this is logging service";
    }

    @PostMapping("/api/logging")
    public Object logging(@RequestBody LogEntity logEntity) {
        LogEntity log = loggingService.logging(logEntity);
        if (log == null) {
            return RestResult.failure().descr("logging failed");
        }
        return RestResult.success(log, "logging success");
    }

    @GetMapping("/api/list")
    public Object list(String service) {
        List list = loggingService.list(service);
        if (list == null) {
            return RestResult.failure().descr("日志获取失败");
        }
        return RestResult.success(list, "日志获取成功");
    }

}
