package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceTypeEntity;

public interface DeviceTypeEntityMapper {

    DeviceType fromEntity(final DeviceTypeEntity entity);

    DeviceTypeEntity toEntity(final DeviceType deviceType);
}
