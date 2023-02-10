package com.ninjaone.backendinterviewproject.domain.exception;

public class DuplicatedDeviceException extends RuntimeException {
    public DuplicatedDeviceException(final String description){
        super(description);
    }
}
