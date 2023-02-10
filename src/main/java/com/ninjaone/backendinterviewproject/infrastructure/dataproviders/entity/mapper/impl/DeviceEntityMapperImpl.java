package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceEntity;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.ServiceEntityMapper;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class DeviceEntityMapperImpl implements DeviceEntityMapper {

    private final DeviceTypeEntityMapper deviceTypeEntityMapper;

    private final ServiceEntityMapper serviceEntityMapper;

    @Override
    public Device fromEntity(DeviceEntity entity) {
        return Device.builder()
                .id(entity.getId())
                .name(entity.getName())
                .serviceCost(entity.getServiceCost())
                .deviceType(deviceTypeEntityMapper.fromEntity(entity.getDeviceType()))
                .serviceList(
                        entity.getServiceList().stream()
                                .map(serviceEntityMapper::fromEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }

    @Override
    public DeviceEntity toEntity(Device device) {
        return DeviceEntity.builder()
                .id(device.getId())
                .name(device.getName())
                .serviceCost(device.getServiceCost())
                .deviceType(deviceTypeEntityMapper.toEntity(device.getDeviceType()))
                .serviceList(
                        device.getServiceList().stream()
                                .map(serviceEntityMapper::toEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
