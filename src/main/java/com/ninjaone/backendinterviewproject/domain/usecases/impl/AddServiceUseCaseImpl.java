package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedServiceException;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.AddServiceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@AllArgsConstructor

public class AddServiceUseCaseImpl implements AddServiceUseCase {

    private final ServiceRepository serviceRepository;

    private final DeviceTypeRepository deviceTypeRepository;

    private final IdGenerator idGenerator;

    @Override
    public Service add(Service service) {
        final Service existingService = serviceRepository.getServiceByIdOrName(service.getId(), service.getName());

        if (existingService == null) {
            final List<DeviceType> existingDeviceTypes =  deviceTypeRepository.getAllByIdList(
                    service.getAcceptedDevices().stream()
                            .map(DeviceType::getId)
                            .collect(Collectors.toList())
            );
            final Service newService = Service.builder()
                    .id(idGenerator.generate())
                    .name(service.getName())
                    .cost(service.getCost())
                    .acceptedDevices(existingDeviceTypes)
                    .build();
            return serviceRepository.saveService(newService);
        } else {
            throw new DuplicatedServiceException(
                    "Service with id ["+ service.getId() +"] or name [" + service.getName() + "] is already registered"
            );
        }
    }
}
