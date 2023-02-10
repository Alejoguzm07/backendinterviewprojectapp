package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper;


import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceRequest;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceResponse;

public interface ServiceMapper {

    Service fromRequest(final ServiceRequest request);

    ServiceResponse toResponse(Service service);
}
