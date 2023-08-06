package com.ksyun.start.camp;

import com.ksyun.start.camp.entity.LogEntity;
import com.ksyun.start.camp.rest.RestResult;
import com.ksyun.start.camp.service.LoggingServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 实现日志服务 API
 */
@Slf4j
@RestController
public class ServiceController {

    @Autowired
    private LoggingServiceImpl loggingService;

    private AtomicInteger logId = new AtomicInteger(0);

    // TODO: 实现日志服务 API
    @RequestMapping("/")
    public String index() {
        return "this is logging service";
    }

    @PostMapping("/api/logging")
    public Object logging(@RequestBody LogEntity logEntity) {
        boolean b = loggingService.logging(logEntity, logId.incrementAndGet());
        if (b) {
            log.info("记录日志成功: {}", logEntity);
            return RestResult.success().descr("记录日志成功").data(logEntity);
        }
        log.info("记录日志失败: {}", logEntity);
        return RestResult.failure().descr("记录日志失败");
    }

    @GetMapping("/api/list")
    public Object list(String service) {
        List list = loggingService.list(service);
        log.info("获取日志条数: {}", list.size());
        return RestResult.success().descr("日志获取成功").data(list);
    }

}
