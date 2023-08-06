package com.ksyun.start.camp.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogEntity implements Comparable<LogEntity> {

    private Integer logId;

    private String serviceName;

    private String serviceId;

    private String datetime;

    private String level;

    private String message;

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || !(obj instanceof LogEntity)) {
            return false;
        }
        LogEntity logEntity = (LogEntity) obj;
        if (logEntity.getServiceName() == null || !serviceName.equals(logEntity.getServiceName()) ||
                logEntity.getServiceId() == null || !serviceId.equals(logEntity.getServiceId()) ||
                logEntity.getDatetime() == null || !datetime.equals(logEntity.getDatetime()) ||
                logEntity.getLevel() == null || !level.equals(logEntity.getLevel()) ||
                logEntity.getMessage() == null || !message.equals(logEntity.getMessage())) {
            return false;
        }
        return true;
    }

    /**
     * 重写 compareTo 方法，用于排序
     */
    @Override
    public int compareTo(LogEntity o) {
        if (equals(o)) {
            return 0;
        }
        return o.getLogId() - logId;
    }
}
