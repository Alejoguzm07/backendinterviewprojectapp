package com.ninjaone.backendinterviewproject.domain.exception;

public class DuplicatedServiceException extends RuntimeException {
    public DuplicatedServiceException(final String description){
        super(description);
    }
}
