package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.UnassignServiceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UnassignServiceUseCaseImpl implements UnassignServiceUseCase {

    private final DeviceRepository deviceRepository;
    @Override
    public Device unassign(String deviceId, String serviceId) {

        final Device device = deviceRepository.getDeviceByID(deviceId);

        if(device != null) {
            device.unassignService(serviceId);
            return deviceRepository.saveDevice(device);
        } else {
            throw new DeviceNotFoundException("Device with id ["+ deviceId +"] does not exist");
        }
    }
}
