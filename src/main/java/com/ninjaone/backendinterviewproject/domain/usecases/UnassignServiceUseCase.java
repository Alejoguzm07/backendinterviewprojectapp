package com.ninjaone.backendinterviewproject.domain.usecases;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;

public interface UnassignServiceUseCase {

    Device unassign(final String deviceId, final String serviceId);
}
