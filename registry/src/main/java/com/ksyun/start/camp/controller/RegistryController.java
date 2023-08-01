package com.ksyun.start.camp.controller;

import com.ksyun.start.camp.entity.ServiceEntity;
import com.ksyun.start.camp.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
public class RegistryController {

    @Autowired
    private RegistryService registryService;

    @RequestMapping("/")
    public String hello() {
        return "hello";
    }

    //    @RequestMapping
    @PostMapping("/api/test")
    public Object Test(@RequestBody String serviceName) {
        System.out.println(serviceName);
        return serviceName;
    }

    @PostMapping("/api/register")
    public Object register(@RequestBody ServiceEntity serviceEntity) {
        if (!vaild(serviceEntity)) {
            return "invalid param";
        }
        return registryService.register(serviceEntity);
    }

    @PostMapping("/api/unregister")
    public Object unregister(@RequestBody ServiceEntity serviceEntity) {
        if (!vaild(serviceEntity)) {
            return "invalid param";
        }
        return registryService.unregister(serviceEntity);
    }

    @PostMapping("/api/heartbeat")
    public Object heartbeat(@RequestBody ServiceEntity serviceEntity) {
        return registryService.heartbeat(serviceEntity);
    }

    @RequestMapping("/api/discovery")
    public Object discovery(String serviceName) {
        return registryService.discovery(serviceName);
    }

    private boolean vaild(ServiceEntity serviceEntity) {
        return serviceEntity != null
                && serviceEntity.getServiceId() != null && !serviceEntity.getServiceId().equals("")
                && serviceEntity.getServiceName() != null && !serviceEntity.getServiceName().equals("")
                && serviceEntity.getIpAddress() != null && !serviceEntity.getIpAddress().equals("")
                && serviceEntity.getPort() != null;
    }
}
