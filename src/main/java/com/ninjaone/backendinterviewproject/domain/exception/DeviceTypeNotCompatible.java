package com.ninjaone.backendinterviewproject.domain.exception;

public class DeviceTypeNotCompatible extends RuntimeException {
    public DeviceTypeNotCompatible(final String description){
        super(description);
    }
}
