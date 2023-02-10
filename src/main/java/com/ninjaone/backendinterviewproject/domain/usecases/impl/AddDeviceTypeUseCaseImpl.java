package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedDeviceTypeException;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.AddDeviceTypeUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class AddDeviceTypeUseCaseImpl implements AddDeviceTypeUseCase {

    private final DeviceTypeRepository deviceTypeRepository;

    private final IdGenerator idGenerator;

    @Override
    public DeviceType add(DeviceType deviceType) {
        final DeviceType existingDeviceType = deviceTypeRepository.getDeviceByIdOrName(deviceType.getId(), deviceType.getName());
        if(existingDeviceType == null) {
            return deviceTypeRepository.add(
                    DeviceType.builder()
                            .id(idGenerator.generate())
                            .name(deviceType.getName())
                            .cost(deviceType.getCost())
                            .build()
            );
        } else {
            throw new DuplicatedDeviceTypeException(
                    "Device type with id ["+ deviceType.getId() +"] or name [" + deviceType.getName() + "] is already registered"
            );
        }
    }
}
