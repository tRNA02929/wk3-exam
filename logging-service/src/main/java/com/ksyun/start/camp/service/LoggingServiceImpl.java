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

    public boolean logging(LogEntity logEntity, int logId) {
        logEntity.setLogId(logId);
        return logSet.add(logEntity);
    }

    public List list(String service) {
        boolean isAll = service == null || service.isEmpty();

        SortedSet<LogEntity> sortedLogSet = new TreeSet<>(this.logSet);
        if (isAll) {
            return new ArrayList(sortedLogSet);
        }
        sortedLogSet.removeIf(data -> !service.equals(data.getServiceId()));
        if (sortedLogSet.size()<=5) {
            return new ArrayList(sortedLogSet);
        }
        return new ArrayList(sortedLogSet).subList(0,5);
    }

}
