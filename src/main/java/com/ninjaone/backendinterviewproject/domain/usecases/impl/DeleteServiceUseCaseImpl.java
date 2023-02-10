package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.NoServiceFoundException;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.DeleteServiceUseCase;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteServiceUseCaseImpl implements DeleteServiceUseCase {

    private final ServiceRepository serviceRepository;

    @Override
    public void delete(final String id) {

        final Service existingService = serviceRepository.getServiceById(id);

        if(existingService != null) {
            serviceRepository.deleteService(id);
        } else {
            throw new NoServiceFoundException("Service with id ["+ id +"] does not exist");
        }
    }
}
