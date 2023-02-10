package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa;

import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.ServiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepositoryJpa extends JpaRepository<ServiceEntity, String> {
    Optional<ServiceEntity> findByIdOrName(String id, String name);

    ServiceEntity save(final ServiceEntity entity);

    void deleteById(final String id);
}
