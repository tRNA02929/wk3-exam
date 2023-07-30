package com.ksyun.start.camp.service;

import com.ksyun.start.camp.entity.LogEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 日志服务的实现
 */
@Component
public class LoggingServiceImpl implements LoggingService {

    private List<LogEntity> logList = new ArrayList<>();

    public LogEntity logging(LogEntity logEntity) {
        logList.add(logEntity);
        return logEntity;
    }

    public List list(String service) {
        boolean isAll = service == null || service.isEmpty();
        List<LogEntity> dataList = new ArrayList<>();
        for (int i = logList.size(); i >=0; i--) {
            LogEntity data = logList.get(i);
            if (!isAll || service.equals(data.getServiceName())) {
                dataList.add(data);
            }
        }
        return dataList;
    }

}
