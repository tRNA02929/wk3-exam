package com.ksyun.start.camp.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LogEntity {

    private Integer logId;

    private String serviceName;

    private String serviceId;

    private String datetime;

    private String level;

    private String message;

}
