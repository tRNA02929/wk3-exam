package com.ksyun.start.camp.service;

import com.ksyun.start.camp.dto.RegisteredServiceDTO;
import com.ksyun.start.camp.entity.ServiceEntity;
import com.ksyun.start.camp.rest.RestResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j(topic = "RegistryService")
public class RegistryService {

    @Autowired
    private RegisteredServiceDTO registeredServices;

    public Object register(ServiceEntity serviceEntity) {
//        registeredServices.clearExpiredService();
        serviceEntity.setHeartBeatTime(System.currentTimeMillis());
        boolean b = registeredServices.addService(serviceEntity);
        if (b) {
            log.info("注册成功,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
            return RestResult.success().descr("注册成功").data(serviceEntity);
        }
        log.info("注册失败,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
        return RestResult.failure().descr("服务已注册").data(serviceEntity);
    }

    public Object unregister(ServiceEntity serviceEntity) {
        boolean b = registeredServices.removeService(serviceEntity);
        if (b) {
            log.info("服务注销成功,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
            return RestResult.success().descr("服务注销成功").data(serviceEntity);
        }
        log.info("服务注销失败,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
        return RestResult.failure().descr("未找到该服务").data(serviceEntity);
    }

    public Object heartbeat(ServiceEntity serviceEntity) {
//        registeredServices.clearExpiredService();
        boolean b = registeredServices.heartbeat(serviceEntity);
        if (b) {
            log.info("心跳更新成功,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
            return RestResult.success().descr("心跳更新成功").data(serviceEntity);
        }
        log.info("心跳更新失败,服务ID:{},ip:{},端口:{}", serviceEntity.getServiceId(), serviceEntity.getIpAddress(), serviceEntity.getPort());
        return RestResult.failure().descr("心跳更新失败").data(serviceEntity);
    }

    public Object discovery(String serviceName) {
//        registeredServices.clearExpiredService();
        List<ServiceEntity> serviceEntities = registeredServices.discovery(serviceName);
        if (serviceEntities != null && serviceEntities.size() > 0) {
            return RestResult.success().descr("服务发现成功").data(serviceEntities);
        }
        return RestResult.failure().descr("未找到该服务").data(serviceEntities);
    }
}
