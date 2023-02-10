package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.CalculateDeviceTotalCostUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CalculateDeviceTotalCostUseCaseImpl implements CalculateDeviceTotalCostUseCase {
    private final DeviceRepository deviceRepository;

    @Override
    public Double calculate(String id) {
        final Device device = deviceRepository.getDeviceByID(id);

        if(device != null) {
            return device.calculateTotalCost();
        } else {
            throw new DeviceNotFoundException("Device with id ["+ id +"] does not exist");
        }
    }
}
