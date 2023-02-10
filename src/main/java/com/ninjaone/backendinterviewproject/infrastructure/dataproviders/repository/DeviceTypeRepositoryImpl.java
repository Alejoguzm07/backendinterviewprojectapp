package com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.entity.mapper.DeviceTypeEntityMapper;
import com.ninjaone.backendinterviewproject.infrastructure.dataproviders.repository.jpa.DeviceTypeRepositoryJpa;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class DeviceTypeRepositoryImpl implements DeviceTypeRepository {

    private final DeviceTypeRepositoryJpa deviceTypeRepositoryJpa;

    private final DeviceTypeEntityMapper deviceTypeEntityMapper;

    @Override
    public DeviceType add(DeviceType deviceType) {
        return deviceTypeEntityMapper.fromEntity(deviceTypeRepositoryJpa.save(deviceTypeEntityMapper.toEntity(deviceType)));
    }

    @Override
    public void delete(String id) {
        deviceTypeRepositoryJpa.deleteById(id);
    }

    @Override
    public DeviceType getDeviceTypeByID(String id) {
        return deviceTypeRepositoryJpa.findById(id).map(deviceTypeEntityMapper::fromEntity).orElse(null);
    }

    @Override
    public List<DeviceType> getAllByIdList(List<String> ids) {
        return deviceTypeRepositoryJpa.findAllByIdIn(ids).stream()
                .map(deviceTypeEntityMapper::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public DeviceType getDeviceByIdOrName(String id, String name) {
        return deviceTypeRepositoryJpa.findByIdOrName(id, name).map(deviceTypeEntityMapper::fromEntity).orElse(null);
    }
}
