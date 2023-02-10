package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.exception.DeviceTypeNotCompatible;
import com.ninjaone.backendinterviewproject.domain.exception.NoServiceFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.AssignServiceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import lombok.AllArgsConstructor;

import javax.management.ServiceNotFoundException;

@AllArgsConstructor
public class AssignServiceUseCaseImpl implements AssignServiceUseCase {

    private final DeviceRepository deviceRepository;

    private final ServiceRepository serviceRepository;

    @Override
    public Device assign(final String deviceId, final String serviceId) {

        final Device device = deviceRepository.getDeviceByID(deviceId);

        if(device != null) {
            final Service service = serviceRepository.getServiceById(serviceId);
            if (service != null){
                if(service.canBeAssigned(device.getDeviceType())) {
                    device.assignService(service);
                    return deviceRepository.saveDevice(device);
                } else {
                    throw new DeviceTypeNotCompatible(
                            "Service with id ["+ serviceId + "] can not be assigned to devices of type with id ["
                            + device.getDeviceType().getId() + "]"
                    );
                }
            } else {
                throw new NoServiceFoundException("Service with id ["+ serviceId +"] does not exist");
            }
        } else {
            throw new DeviceNotFoundException("Device with id ["+ deviceId +"] does not exist");
        }
    }
}
