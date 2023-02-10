package com.ninjaone.backendinterviewproject.domain.usecases;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;

public interface AddServiceUseCase {

    Service add(final Service service);
}
