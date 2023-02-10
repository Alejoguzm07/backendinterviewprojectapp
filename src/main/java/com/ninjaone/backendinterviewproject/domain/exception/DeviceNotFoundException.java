package com.ninjaone.backendinterviewproject.domain.exception;

public class DeviceNotFoundException extends RuntimeException {
    public DeviceNotFoundException(final String description){
        super(description);
    }
}
