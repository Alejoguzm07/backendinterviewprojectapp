package com.ninjaone.backendinterviewproject.domain.model.device;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class Device {

    private String id;

    private String name;

    private DeviceType deviceType;

    private List<Service> serviceList;

    private Double serviceCost;

    public Double calculateTotalCost() {
        return deviceType.getCost() + serviceCost;
    }

    public void assignService(final Service service) {
        serviceCost = serviceCost + service.getCost();
        serviceList.add(service);
    }

    public void unassignService(final String serviceId) {
        serviceList.removeIf(service -> {
            if(service.getId().equals(serviceId)){
                serviceCost = serviceCost - service.getCost();
                return service.getId().equals(serviceId);
            } else {
                return false;
            }
        });
    }
}
