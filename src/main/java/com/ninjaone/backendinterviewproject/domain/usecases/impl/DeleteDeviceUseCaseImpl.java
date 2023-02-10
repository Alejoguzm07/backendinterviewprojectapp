package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.DeleteDeviceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteDeviceUseCaseImpl implements DeleteDeviceUseCase {

    private final DeviceRepository deviceRepository;

    @Override
    public void delete(final String id) {

        final Device existingDevice = deviceRepository.getDeviceByID(id);

        if(existingDevice != null) {
            deviceRepository.deleteDevice(id);
        } else {
            throw new DeviceNotFoundException("Device with id ["+ id +"] does not exist");
        }

    }
}
