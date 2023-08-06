package com.ksyun.start.camp.service;

import com.ksyun.start.camp.entity.LogEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 日志服务的实现
 */
@Component
public class LoggingServiceImpl implements LoggingService {

    private Set<LogEntity> logSet;

    public LoggingServiceImpl() {
        this.logSet = new ConcurrentSkipListSet<>();
    }

    public boolean logging(LogEntity logEntity, int logId) {
        logEntity.setLogId(logId);
        return logSet.add(logEntity);
    }

    public List list(String service) {
        boolean isAll = service == null || service.isEmpty();
        List<LogEntity> ansLogList = new ArrayList<>(this.logSet);

        if (isAll) {
            return ansLogList;
        }

        ansLogList.removeIf(data -> !service.equals(data.getServiceId()));
        if (ansLogList.size() <= 5) {
            return ansLogList;
        }
        return ansLogList.subList(0, 5);
    }

}
