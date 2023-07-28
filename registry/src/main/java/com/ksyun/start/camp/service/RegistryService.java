package com.ksyun.start.camp.service;

import com.ksyun.start.camp.entity.ServiceEntity;
import org.springframework.stereotype.Service;

@Service
public class RegistryService {

    public String register(ServiceEntity serviceEntity) {
        // TODO
        return "register";
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
