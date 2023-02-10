package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteDeviceUseCaseImplTest {

    private final String DEFAULT_DEVICE_ID = "e4db3a4c-a8a3-11ed-afa1-0242ac120002";

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private DeleteDeviceUseCaseImpl useCase;

    @Test()
    @DisplayName("Should delete a device when it already exists.")
    void shouldBeDeleted() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Device device = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(7.0)
                .serviceList(new ArrayList<>())
                .build();

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(device);

        useCase.delete(DEFAULT_DEVICE_ID);

    }

    @Test()
    @DisplayName("Should throw a DeviceNotFoundException exception when the device does not exist.")
    void shouldFailWhenDuplicated() {

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(null);

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> useCase.delete(DEFAULT_DEVICE_ID));

        verify(deviceRepository, never()).deleteDevice(anyString());
        final String expected =
                "Device with id [e4db3a4c-a8a3-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}