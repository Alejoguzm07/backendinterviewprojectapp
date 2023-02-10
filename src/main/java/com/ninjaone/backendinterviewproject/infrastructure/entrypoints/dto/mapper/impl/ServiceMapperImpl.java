package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceTypeMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.ServiceMapper;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceMapperImpl implements ServiceMapper {

    private final DeviceTypeMapper deviceTypeMapper;
    @Override
    public Service fromRequest(ServiceRequest request) {
        final List<DeviceType> acceptedDevices = request.getAcceptedDevices() != null ?
                request.getAcceptedDevices().stream()
                        .map(
                                type -> DeviceType.builder()
                                        .id(type)
                                        .build()
                        )
                        .collect(Collectors.toList()) :
                new ArrayList<>();

        return Service.builder()
                .name(request.getName())
                .cost(request.getCost())
                .acceptedDevices(acceptedDevices)
                .build();
    }

    @Override
    public ServiceResponse toResponse(Service service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .cost(service.getCost())
                .acceptedDevices(
                        service.getAcceptedDevices().stream()
                                .map(deviceTypeMapper::toResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
