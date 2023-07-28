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
        // TODO
        serviceEntity.setHeartBeatTime(System.currentTimeMillis());
        boolean b = registeredServices.addService(serviceEntity);
        return serviceEntity;
    }

    public String unregister(ServiceEntity serviceEntity) {
        // TODO
        return "unregister";
    }

    public String heartbeat(ServiceEntity serviceEntity) {
        // TODO
        return "heartbeat";
    }

    public String discovery(String serviceName) {
        // TODO
        return "discovery";
    }
}
