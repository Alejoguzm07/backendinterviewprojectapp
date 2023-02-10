package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa;

import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepositoryJpa extends JpaRepository<DeviceEntity, String> {

    @Override
    List<DeviceEntity> findAll();

    Optional<DeviceEntity> findById(final String id);
    Optional<DeviceEntity> findByIdOrName(final String id, final String name);

    DeviceEntity save(final DeviceEntity device);

    void deleteById(final String id);

}
