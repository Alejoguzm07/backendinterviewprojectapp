package com.ninjaone.backendinterviewproject.domain.exception;

public class DeviceTypeNotFound extends RuntimeException {
    public DeviceTypeNotFound(final String description){
        super(description);
    }
}
