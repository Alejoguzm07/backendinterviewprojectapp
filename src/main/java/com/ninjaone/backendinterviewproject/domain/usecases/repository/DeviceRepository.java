package com.ninjaone.backendinterviewproject.domain.usecases.repository;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;

import java.util.List;

public interface DeviceRepository {

    List<Device> getAllDevices();

    Device getDeviceByID(final String deviceId);

    Device saveDevice(final Device device);

    void deleteDevice(final String id);

    Device getDeviceByIdOrName(final String id, final String name);
}
