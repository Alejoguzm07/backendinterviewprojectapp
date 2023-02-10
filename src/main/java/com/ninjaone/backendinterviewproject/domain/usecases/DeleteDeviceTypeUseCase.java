package com.ninjaone.backendinterviewproject.domain.usecases;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;

public interface DeleteDeviceTypeUseCase {
    void delete(final String id);
}
