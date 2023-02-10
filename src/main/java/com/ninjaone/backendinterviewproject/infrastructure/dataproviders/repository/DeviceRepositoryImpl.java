package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.DeviceEntity;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.DeviceRepositoryJpa;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DeviceRepositoryImpl implements DeviceRepository {

    private final DeviceRepositoryJpa deviceRepositoryJpa;

    private final DeviceEntityMapper deviceEntityMapper;

    @Override
    public List<Device> getAllDevices() {

        return deviceRepositoryJpa.findAll().stream()
                .map(deviceEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public Device getDeviceByID(String deviceId) {
        return deviceRepositoryJpa.findById(deviceId).map(deviceEntityMapper::fromEntity).orElse(null);
    }

    @Override
    public Device getDeviceByIdOrName(final String id, final String name) {
        return deviceRepositoryJpa.findByIdOrName(id, name).map(deviceEntityMapper::fromEntity).orElse(null);
    }

    @Override
    public Device saveDevice(Device device) {

        return deviceEntityMapper.fromEntity(deviceRepositoryJpa.save(deviceEntityMapper.toEntity(device)));
    }

    @Override
    public void deleteDevice(final String id) {
        deviceRepositoryJpa.deleteById(id);
    }


}
