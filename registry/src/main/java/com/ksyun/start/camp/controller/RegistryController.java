package com.ksyun.start.camp.controller;

import com.ksyun.start.camp.entity.ServiceEntity;
import com.ksyun.start.camp.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/api/register")
    public Object register(ServiceEntity serviceEntity) {
        if (!vaild(serviceEntity)) {
            return "invalid param";
        }
        return registryService.register(serviceEntity);
    }

    @RequestMapping("/api/unregister")
    public Object unregister(ServiceEntity serviceEntity) {
        if (!vaild(serviceEntity)) {
            return "invalid param";
        }
        return registryService.unregister(serviceEntity);
    }

    @RequestMapping("/api/heartbeat")
    public Object heartbeat(ServiceEntity serviceEntity) {
        return registryService.heartbeat(serviceEntity);
    }

    @RequestMapping("/api/discovery")
    public String discovery(String serviceName) {
        return registryService.discovery(serviceName);
    }

    private boolean vaild(ServiceEntity serviceEntity) {
        return serviceEntity != null
                && serviceEntity.getServiceId() != null
                && serviceEntity.getServiceName() != null
                && serviceEntity.getIpAddress() != null
                && serviceEntity.getPort() != null;
    }
}
