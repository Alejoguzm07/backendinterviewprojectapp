package com.ninjaone.backendinterviewproject.domain.usecases;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;

public interface AddDeviceUseCase {

    Device add(final Device device);
}
