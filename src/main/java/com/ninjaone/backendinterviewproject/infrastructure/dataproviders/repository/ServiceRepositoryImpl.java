package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.ServiceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.ServiceRepositoryJpa;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class ServiceRepositoryImpl implements ServiceRepository {

    private final ServiceRepositoryJpa serviceRepositoryJpa;

    private final ServiceEntityMapper serviceEntityMapper;

    @Override
    public Service getServiceById(String serviceId) {
        return serviceRepositoryJpa.findById(serviceId).map(serviceEntityMapper::fromEntity).orElse(null);
    }

    @Override
    public Service saveService(Service service) {
        return serviceEntityMapper.fromEntity(serviceRepositoryJpa.save(serviceEntityMapper.toEntity(service)));
    }

    @Override
    public void deleteService(final String id) {
        serviceRepositoryJpa.deleteById(id);

    }

    @Override
    public Service getServiceByIdOrName(String id, String name) {
        return serviceRepositoryJpa.findByIdOrName(id, name).map(serviceEntityMapper::fromEntity).orElse(null);
    }
}
