package com.ksyun.start.camp.dto;

import com.ksyun.start.camp.entity.ServiceEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class RegisteredServiceDTO {

    @Value("${heart}")
    private int heart;

    private HashSet<ServiceEntity> services;

    public RegisteredServiceDTO() {
        this.services = new HashSet<>();
    }

    /**
     * 添加服务：
     * 服务的id相同，就认为是同一个服务；
     * 服务的地址一样，就认为是同一个服务
     *
     * @param service
     * @return boolean 是否添加成功
     */
    public boolean addService(ServiceEntity service) {
        return services.add(service);
    }

    /**
     * 删除服务：
     * 必须服务的id,服务名，地址一样，才能删除
     *
     * @param service 服务
     * @return boolean 是否删除成功
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
     * @param service 服务
     * @return boolean 是否更新成功
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

    /**
     * 服务发现：
     * 根据服务名，返回符合的服务，如果参数为空，返回所有服务
     *
     * @param serviceName 服务名
     * @return ArrayList<ServiceEntity> 符合的服务
     */
    public List<ServiceEntity> discovery(String serviceName) {
        if (serviceName == null) {
            return new ArrayList<>(services);
        }
        List<ServiceEntity> result = services.stream()
                .filter(serviceEntity -> serviceEntity.getServiceName().equals(serviceName))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 清除过期服务：
     */
    public void clearExpiredService() {
        List<ServiceEntity> expiredServices = services.stream()
                .filter(serviceEntity -> System.currentTimeMillis() - serviceEntity.getHeartBeatTime() > heart * 1000)
                .collect(Collectors.toList());
        services.removeAll(expiredServices);
    }
}
