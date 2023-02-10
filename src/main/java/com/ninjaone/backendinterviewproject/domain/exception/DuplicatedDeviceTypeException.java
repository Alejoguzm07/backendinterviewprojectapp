package com.ninjaone.backendinterviewproject.domain.exception;

public class DuplicatedDeviceTypeException extends RuntimeException {
    public DuplicatedDeviceTypeException(final String description){
        super(description);
    }
}
