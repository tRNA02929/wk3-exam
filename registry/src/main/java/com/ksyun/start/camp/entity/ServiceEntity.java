package com.ksyun.start.camp.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Objects;

@Data
@Builder
public class ServiceEntity {

    /**
     * 服务唯一标识，即服务ID
     */
    private Integer serviceId;

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


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceEntity) {
            ServiceEntity serviceEntity = (ServiceEntity) obj;
            if (Objects.equals(serviceEntity.getServiceId(), this.getServiceId())) {
                return true;
            }
            if (Objects.equals(serviceEntity.getIpAddress(), this.getIpAddress())
                    && Objects.equals(serviceEntity.getPort(), this.getPort())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return serviceId;
    }
}
