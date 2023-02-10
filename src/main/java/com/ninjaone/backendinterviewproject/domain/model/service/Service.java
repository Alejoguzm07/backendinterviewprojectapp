package com.ninjaone.backendinterviewproject.domain.model.service;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class Service {

    private String id;

    private String name;

    private Double cost;

    private List<DeviceType> acceptedDevices;

    public boolean canBeAssigned(final DeviceType deviceType) {
        return acceptedDevices.contains(deviceType);
    }
}
