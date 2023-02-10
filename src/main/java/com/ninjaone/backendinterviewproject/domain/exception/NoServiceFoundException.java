package com.ninjaone.backendinterviewproject.domain.exception;

public class NoServiceFoundException extends RuntimeException {
    public NoServiceFoundException(final String description){
        super(description);
    }
}
