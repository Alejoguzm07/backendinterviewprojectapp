package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceResponse;

public interface DeviceMapper {

    Device fromRequest(final DeviceRequest dto);

    DeviceResponse toResponse(Device device);
}
