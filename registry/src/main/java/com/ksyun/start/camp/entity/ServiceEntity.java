package com.ksyun.start.camp.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ServiceEntity {

    /**
     * 服务唯一标识，即服务ID
     */
    private String serviceId;

    /**
     * 服务名称
     */
    private String serviceName;

    /**
     * 服务ip地址
     */
    private String ipAddress;

    /**
     * 服务端口号
     */
    private Integer port;

    /**
     * 服务心跳时间（60秒超时）
     */
    private Long heartBeatTime;
}
