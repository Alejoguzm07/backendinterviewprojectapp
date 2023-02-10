package com.ninjaone.backendinterviewproject.domain.usecases.repository;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;

import java.util.List;

public interface DeviceTypeRepository {

    DeviceType add(final DeviceType deviceType);

    void delete(final String id);

    DeviceType getDeviceTypeByID(final String id);

    List<DeviceType> getAllByIdList(final List<String> collect);

    DeviceType getDeviceByIdOrName(final String id, final String name);
}
