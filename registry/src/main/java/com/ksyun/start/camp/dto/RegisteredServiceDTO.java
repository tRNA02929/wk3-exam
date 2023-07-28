package com.ksyun.start.camp.dto;

import com.ksyun.start.camp.entity.ServiceEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@Getter
@Setter
public class RegisteredServiceDTO {

    private HashSet<ServiceEntity> services;

    public RegisteredServiceDTO() {
        this.services = new HashSet<>();
    }

    /**
     * 添加服务：
     * 服务的id相同，就认为是同一个服务
     * 服务的地址一样，就认为是同一个服务
     *
     * @param service
     * @return
     */
    public boolean addService(ServiceEntity service) {
        return services.add(service);
    }

    /**
     * 删除服务：
     * 必须服务的id,服务名，地址一样，才能删除
     *
     * @param service
     * @return
     */
    public boolean removeService(ServiceEntity service) {
        ServiceEntity temp = null;
        for (ServiceEntity serviceEntity : services) {
            if (serviceEntity.getServiceId().equals(service.getServiceId())
                    && serviceEntity.getServiceName().equals(service.getServiceName())
                    && serviceEntity.equalsAddress(service.getIpAddress(), service.getPort())) {
                temp = serviceEntity;
                break;
            }
        }
        return services.remove(temp);
    }

    /**
     * 客户端发送心跳：
     * 必须服务的id,地址一样，才能更新心跳时间
     *
     * @param service
     * @return
     */
    public boolean heartbeat(ServiceEntity service) {
        for (ServiceEntity serviceEntity : services) {
            if (serviceEntity.getServiceId().equals(service.getServiceId())
                    && serviceEntity.equalsAddress(service.getIpAddress(), service.getPort())) {
                serviceEntity.setHeartBeatTime(System.currentTimeMillis());
                return true;
            }
        }
        return false;
    }
}
