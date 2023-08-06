package com.ksyun.start.camp.dto;

import com.ksyun.start.camp.entity.ServiceEntity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.stream.Collectors;

@Component
@Getter
@Setter
public class RegisteredServiceDTO {

    @Value("${heart}")
    private int heart;

    // 使用线程安全的ConcurrentSkipListSet，作为存储结构
    private Set<ServiceEntity> clients;

    private Set<ServiceEntity> timeServices;

    public RegisteredServiceDTO() {
        this.clients = new ConcurrentSkipListSet<>();
        this.timeServices = new ConcurrentSkipListSet<>();
    }

    /**
     * 添加服务：
     * 服务的id相同，就认为是同一个服务；
     * 服务的地址一样，就认为是同一个服务
     *
     * @param service 服务
     * @return boolean 是否添加成功
     */
    public boolean addService(ServiceEntity service) {
        if (service.getServiceName().equals("time-service")) {
            return timeServices.add(service);
        }
        return clients.add(service);
    }

    /**
     * 删除服务：
     * 必须服务的id,服务名，地址一样，才能删除
     *
     * @param service 服务
     * @return boolean 是否删除成功
     */
    public boolean removeService(ServiceEntity service) {
        Set<ServiceEntity> goalService = getGoalService(service.getServiceName());
        ServiceEntity temp = null;
        for (ServiceEntity serviceEntity : goalService) {
            if (serviceEntity.getServiceId().equals(service.getServiceId())
                    && serviceEntity.getServiceName().equals(service.getServiceName())
                    && serviceEntity.equalsAddress(service.getIpAddress(), service.getPort())) {
                temp = serviceEntity;
                break;
            }
        }
        if (temp == null) {
            return false;
        }
        return goalService.remove(temp);
    }

    /**
     * 客户端发送心跳：
     * 必须服务的id,地址一样，才能更新心跳时间
     *
     * @param service 服务
     * @return boolean 是否更新成功
     */
    public boolean heartbeat(ServiceEntity service) {
        Set<ServiceEntity> goalService = new HashSet<>(clients);
        goalService.addAll(timeServices);
        for (ServiceEntity serviceEntity : goalService) {
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
            List<ServiceEntity> result = new ArrayList<>(timeServices);
            result.addAll(clients);
            return result;
        }
        ConcurrentSkipListSet<ServiceEntity> goalService = new ConcurrentSkipListSet<>(getGoalService(serviceName));
        List<ServiceEntity> result = new ArrayList<>();
        if (goalService.isEmpty()) {
            return result;
        }
        ServiceEntity serviceEntity = goalService.first();
        serviceEntity.setPollCount(serviceEntity.getPollCount() + 1);
        result.add(serviceEntity);
        return result;
    }

    /**
     * 清除过期服务：
     */
    @PreDestroy
    public void clearExpiredService() {
        timeServices.removeIf(serviceEntity -> System.currentTimeMillis() - serviceEntity.getHeartBeatTime() > heart * 1000);
        clients.removeIf(serviceEntity -> System.currentTimeMillis() - serviceEntity.getHeartBeatTime() > heart * 1000);
    }

    /**
     * 获取目标服务queue，并清除过期服务：
     * 懒清除，只有在获取服务时才清除过期服务
     */
    private Set<ServiceEntity> getGoalService(String serviceName) {
        if (serviceName.equals("time-service")) {
            timeServices.removeIf(serviceEntity -> System.currentTimeMillis() - serviceEntity.getHeartBeatTime() > heart * 1000);
            return timeServices;
        } else {
            clients.removeIf(serviceEntity -> System.currentTimeMillis() - serviceEntity.getHeartBeatTime() > heart * 1000);
            return clients;
        }
    }
}
