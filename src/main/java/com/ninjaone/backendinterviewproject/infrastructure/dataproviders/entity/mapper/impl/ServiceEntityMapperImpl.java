package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.ServiceEntity;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.ServiceEntityMapper;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class ServiceEntityMapperImpl implements ServiceEntityMapper {

    private final DeviceTypeEntityMapper deviceTypeEntityMapper;

    @Override
    public Service fromEntity(final ServiceEntity entity) {
        return Service.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cost(entity.getCost())
                .acceptedDevices(
                        entity.getAcceptedDevices().stream()
                                .map(deviceTypeEntityMapper::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public ServiceEntity toEntity(final Service service) {
        return ServiceEntity.builder()
                .id(service.getId())
                .name(service.getName())
                .cost(service.getCost())
                .acceptedDevices(service.getAcceptedDevices().stream()
                        .map(deviceTypeEntityMapper::toEntity)
                        .collect(Collectors.toList()))
                .build();
    }
}
