package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceTypeNotFound;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteDeviceTypeUseCaseImplTest {

    private final String DEFAULT_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @InjectMocks
    private DeleteDeviceTypeUseCaseImpl useCase;

    @Test()
    @DisplayName("Should delete a device type when it already exists.")
    void shouldBeDeleted() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        when(deviceTypeRepository.getDeviceTypeByID(eq(DEFAULT_ID)))
                .thenReturn(deviceType);

        useCase.delete(DEFAULT_ID);

    }

    @Test()
    @DisplayName("Should throw a DeviceTypeNotFound exception when the device type does not exist.")
    void shouldFailWhenDuplicated() {

        when(deviceTypeRepository.getDeviceTypeByID(eq(DEFAULT_ID)))
                .thenReturn(null);

        DeviceTypeNotFound exception = assertThrows(DeviceTypeNotFound.class, () -> useCase.delete(DEFAULT_ID));

        verify(deviceTypeRepository, never()).delete(anyString());
        final String expected =
                "Device type with id [add4a136-a895-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}