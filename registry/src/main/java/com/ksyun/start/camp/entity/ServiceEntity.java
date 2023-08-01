package com.ksyun.start.camp.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Data
@Builder
public class ServiceEntity implements Serializable {

    /**
     * 服务唯一标识，即服务ID
     */
    private Long serviceId;

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

    /**
     * 服务轮询次数
     */
    private int pollCount;

    /**
     * 判断两个服务地址是否相同
     * 只有当ipAddress和port都相同时才代表地址相同
     *
     * @param ipAddress, port
     * @return
     */
    public boolean equalsAddress(String ipAddress, Integer port) {
        if (Objects.equals(ipAddress, this.getIpAddress())
                && Objects.equals(port, this.getPort())) {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ServiceEntity) {
            ServiceEntity serviceEntity = (ServiceEntity) obj;
            if (Objects.equals(serviceEntity.getServiceId(), this.getServiceId())) {
                return true;
            }
            if (equalsAddress(serviceEntity.getIpAddress(), serviceEntity.getPort())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
