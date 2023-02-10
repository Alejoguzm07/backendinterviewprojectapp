package com.ninjaone.backendinterviewproject.domain.usecases;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;

public interface AddDeviceTypeUseCase {

    DeviceType add(final DeviceType deviceType);
}
