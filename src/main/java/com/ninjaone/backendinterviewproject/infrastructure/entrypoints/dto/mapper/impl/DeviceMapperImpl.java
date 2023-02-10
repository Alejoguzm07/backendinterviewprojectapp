package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.DeviceTypeMapper;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.ServiceMapper;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;

@AllArgsConstructor
public class DeviceMapperImpl implements DeviceMapper {

    private final DeviceTypeMapper deviceTypeMapper;

    private final ServiceMapper serviceMapper;

    public Device fromRequest(final DeviceRequest request){

        return Device.builder()
                .name(request.getName())
                .deviceType(
                        DeviceType.builder()
                                .id(request.getDeviceType())
                                .build()
                )
                .build();
    }

    public DeviceResponse toResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .name(device.getName())
                .deviceType(deviceTypeMapper.toResponse(device.getDeviceType()))
                .serviceList(
                        device.getServiceList().stream()
                                .map(serviceMapper::toResponse)
                                .collect(Collectors.toList())
                )
                .build();
    }
}
