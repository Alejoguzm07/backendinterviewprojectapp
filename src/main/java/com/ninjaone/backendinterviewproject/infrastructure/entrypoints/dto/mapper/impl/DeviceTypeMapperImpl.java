package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceTypeMapper;

public class DeviceTypeMapperImpl implements DeviceTypeMapper {
    @Override
    public DeviceType fromRequest(DeviceTypeRequest dto) {
        return DeviceType.builder()
                .name(dto.getName())
                .cost(dto.getCost())
                .build();
    }

    @Override
    public DeviceTypeResponse toResponse(DeviceType deviceType) {
        return DeviceTypeResponse.builder()
                .id(deviceType.getId())
                .name(deviceType.getName())
                .cost(deviceType.getCost())
                .build();
    }
}
