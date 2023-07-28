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

    public boolean addService(ServiceEntity service) {
        if (service.getServiceId() == null) {
            service.setServiceId(services.size() + 1);
        }
        return services.add(service);
    }
}
