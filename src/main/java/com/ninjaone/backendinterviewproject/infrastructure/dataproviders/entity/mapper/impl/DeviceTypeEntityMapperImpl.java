package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceTypeEntity;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;

public class DeviceTypeEntityMapperImpl implements DeviceTypeEntityMapper {
    @Override
    public DeviceType fromEntity(DeviceTypeEntity entity) {
        return DeviceType.builder()
                .id(entity.getId())
                .name(entity.getName())
                .cost(entity.getCost())
                .build();
    }

    @Override
    public DeviceTypeEntity toEntity(DeviceType deviceType) {
        return DeviceTypeEntity.builder()
                .id(deviceType.getId())
                .name(deviceType.getName())
                .cost(deviceType.getCost())
                .build();
    }
}
