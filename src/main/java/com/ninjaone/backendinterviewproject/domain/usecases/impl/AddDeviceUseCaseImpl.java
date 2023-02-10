package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceTypeNotFound;
import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedDeviceException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.AddDeviceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
public class AddDeviceUseCaseImpl implements AddDeviceUseCase {

    private final DeviceRepository deviceRepository;

    private final DeviceTypeRepository deviceTypeRepository;

    private final IdGenerator idGenerator;

    @Override
    public Device add(final Device device) {

        final Device existingDevice = deviceRepository.getDeviceByIdOrName(device.getId(), device.getName());

        if(existingDevice == null) {
            final DeviceType existingDeviceType = deviceTypeRepository.getDeviceTypeByID(device.getDeviceType().getId());
            if(existingDeviceType != null) {
                return deviceRepository.saveDevice(
                        Device.builder()
                                .id(idGenerator.generate())
                                .name(device.getName())
                                .serviceCost(0.0)
                                .deviceType(existingDeviceType)
                                .serviceList(new ArrayList<>())
                                .build()
                );
            } else {
                throw new DeviceTypeNotFound(
                        "Device type with id ["+ device.getDeviceType().getId() +"] does not exist"
                );
            }
        } else {
            throw new DuplicatedDeviceException(
                    "Device with id ["+ device.getId() +"] or name [" + device.getName() + "] is already registered"
            );
        }
    }
}
