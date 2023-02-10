package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceTypeNotFound;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.DeleteDeviceTypeUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteDeviceTypeUseCaseImpl implements DeleteDeviceTypeUseCase {

    private final DeviceTypeRepository deviceTypeRepository;

    @Override
    public void delete(final String id) {

        final DeviceType existingTypeDevice = deviceTypeRepository.getDeviceTypeByID(id);

        if(existingTypeDevice != null) {
            deviceTypeRepository.delete(id);
        } else {
          throw new DeviceTypeNotFound("Device type with id ["+ id +"] does not exist");
        }
    }
}
