package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.ServiceEntity;

public interface ServiceEntityMapper {

    Service fromEntity(final ServiceEntity entity);

    ServiceEntity toEntity(final Service service);
}
