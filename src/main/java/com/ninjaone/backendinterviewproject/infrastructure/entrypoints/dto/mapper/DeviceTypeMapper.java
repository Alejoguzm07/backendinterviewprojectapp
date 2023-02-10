package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeResponse;

public interface DeviceTypeMapper {

    DeviceType fromRequest(final DeviceTypeRequest dto);

    DeviceTypeResponse toResponse(DeviceType deviceType);
}
