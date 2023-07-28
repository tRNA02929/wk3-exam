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
     * 服务的ip和端口号一样，就认为是同一个服务
     *
     * @param service
     * @return
     */
    public boolean addService(ServiceEntity service) {
        return services.add(service);
    }

    /**
     * 删除服务：
     * 必须服务的id,服务名，ip，端口号一样，才能删除
     *
     * @param service
     * @return
     */
    public boolean removeService(ServiceEntity service) {
        ServiceEntity temp = null;
        for (ServiceEntity serviceEntity : services) {
            if (serviceEntity.getServiceId().equals(service.getServiceId())
                    && serviceEntity.getServiceName().equals(service.getServiceName())
                    && serviceEntity.getIpAddress().equals(service.getIpAddress())
                    && serviceEntity.getPort().equals(service.getPort())) {
                temp = serviceEntity;
                break;
            }
        }
        return services.remove(temp);
    }
}
