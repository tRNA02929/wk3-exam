package com.ksyun.start.camp.service;

import com.ksyun.start.camp.dto.RegisteredServiceDTO;
import com.ksyun.start.camp.entity.ServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistryService {

    @Autowired
    private RegisteredServiceDTO registeredServices;

    public Object register(ServiceEntity serviceEntity) {
        registeredServices.clearExpiredService();
        serviceEntity.setHeartBeatTime(System.currentTimeMillis());
        boolean b = registeredServices.addService(serviceEntity);
        return registeredServices.getServices().toArray();
    }

    public Object unregister(ServiceEntity serviceEntity) {
        boolean b = registeredServices.removeService(serviceEntity);
        return registeredServices.getServices().toArray();
    }

    public Object heartbeat(ServiceEntity serviceEntity) {
        registeredServices.clearExpiredService();
        return registeredServices.heartbeat(serviceEntity);
    }

    public Object discovery(String serviceName) {
        registeredServices.clearExpiredService();
        return registeredServices.discovery(serviceName);
    }
}
