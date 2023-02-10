package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.CalculateDeviceServiceCostUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateDeviceServiceCostUseCaseImpl implements CalculateDeviceServiceCostUseCase {

    private final DeviceRepository deviceRepository;

    @Override
    public Double calculate(String id) {

        final Device device = deviceRepository.getDeviceByID(id);

        if(device != null) {
            return device.getServiceCost();
        } else {
            throw new DeviceNotFoundException("Device with id ["+ id +"] does not exist");
        }
    }
}
