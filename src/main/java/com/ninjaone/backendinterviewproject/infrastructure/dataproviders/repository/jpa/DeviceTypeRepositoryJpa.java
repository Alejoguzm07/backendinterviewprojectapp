package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa;

import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceTypeRepositoryJpa extends JpaRepository<DeviceTypeEntity, String> {

    DeviceTypeEntity save(final DeviceTypeEntity deviceType);

    void deleteById(final String id);

    List<DeviceTypeEntity> findAllByIdIn(final List<String> ids);

    Optional<DeviceTypeEntity> findByIdOrName(final String id, final String name);
}
