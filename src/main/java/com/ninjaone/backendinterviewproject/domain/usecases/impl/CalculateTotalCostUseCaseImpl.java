package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.CalculateTotalCostUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CalculateTotalCostUseCaseImpl implements CalculateTotalCostUseCase {

    private final DeviceRepository deviceRepository;
    @Override
    public Double calculate() {
        final List<Device> devices = deviceRepository.getAllDevices();
        return devices.stream()
                .map(Device::calculateTotalCost)
                .reduce(0.0, Double::sum);
    }
}
