package com.ksyun.start.camp.service;

import com.ksyun.start.camp.entity.LogEntity;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 日志服务的实现
 */
@Component
public class LoggingServiceImpl implements LoggingService {

    private Set<LogEntity> logSet;

    public LoggingServiceImpl() {
        this.logSet = new HashSet<>();
    }

    public boolean logging(LogEntity logEntity) {
        logEntity.setLogId(logSet.size());
        return logSet.add(logEntity);
    }

    public List list(String service) {
        boolean isAll = service == null || service.isEmpty();

        SortedSet<LogEntity> sortedLogSet = new TreeSet<>(this.logSet);
        if (isAll) {
            return new ArrayList(sortedLogSet);
        }
        sortedLogSet.removeIf(data -> !service.equals(data.getServiceName()));
        return new ArrayList(sortedLogSet);
    }

}
