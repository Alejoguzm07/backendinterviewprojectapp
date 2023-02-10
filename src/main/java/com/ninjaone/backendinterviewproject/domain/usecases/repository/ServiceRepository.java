package com.ninjaone.backendinterviewproject.domain.usecases.repository;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;

import java.util.List;

public interface ServiceRepository {

    Service getServiceById(final String serviceId);

    Service saveService(final Service service);

    void deleteService(final String id);

    Service getServiceByIdOrName(final String id, final String name);
}
