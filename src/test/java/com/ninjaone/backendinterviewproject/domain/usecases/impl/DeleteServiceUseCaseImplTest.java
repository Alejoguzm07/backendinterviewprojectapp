package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.NoServiceFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteServiceUseCaseImplTest {

    private final String DEFAULT_SERVICE_ID = "e4db3a4c-a8a3-11ed-afa1-0242ac120002";

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private ServiceRepository serviceRepository;

    @InjectMocks
    private DeleteServiceUseCaseImpl useCase;

    @Test()
    @DisplayName("Should delete a device type when it already exists.")
    void shouldBeDeleted() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service = Service.builder()
                .id(DEFAULT_SERVICE_ID)
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        when(serviceRepository.getServiceById(eq(DEFAULT_SERVICE_ID)))
                .thenReturn(service);

        useCase.delete(DEFAULT_SERVICE_ID);

    }

    @Test()
    @DisplayName("Should throw a NoServiceFoundException exception when the service does not exist.")
    void shouldFailWhenDuplicated() {

        when(serviceRepository.getServiceById(eq(DEFAULT_SERVICE_ID)))
                .thenReturn(null);

        NoServiceFoundException exception = assertThrows(NoServiceFoundException.class, () -> useCase.delete(DEFAULT_SERVICE_ID));

        verify(serviceRepository, never()).deleteService(anyString());
        final String expected =
                "Service with id [e4db3a4c-a8a3-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}