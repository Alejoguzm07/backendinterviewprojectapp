package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.handler;

import com.ninjaone.backendinterviewproject.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(
            value = {
                    DuplicatedDeviceException.class,
                    DuplicatedDeviceTypeException.class,
                    DuplicatedServiceException.class
            }
    )
    protected ResponseEntity<String> handleDuplicates(RuntimeException ex) {

        return new ResponseEntity<>(
                "There was a problem with your request, an element was duplicated: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(
            value = {
                    DeviceNotFoundException.class,
                    DeviceTypeNotFound.class,
                    NoServiceFoundException.class
            }
    )
    protected ResponseEntity<String> handleNotFound(RuntimeException ex) {

        return new ResponseEntity<>(
                "There was a problem with your request, an element was not found: " + ex.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(value = { DeviceTypeNotCompatible.class })
    protected ResponseEntity<String> handleNotCompatible(RuntimeException ex) {

        return new ResponseEntity<>(
                "There was a problem with your request: " + ex.getMessage(),
                HttpStatus.BAD_REQUEST
        );
    }

}
