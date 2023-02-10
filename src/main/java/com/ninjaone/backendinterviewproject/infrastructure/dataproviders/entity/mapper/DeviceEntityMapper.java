package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceEntity;

public interface DeviceEntityMapper {

    Device fromEntity(DeviceEntity entity);

    DeviceEntity toEntity(Device device);
}
