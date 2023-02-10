package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceNotFoundException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UnassignServiceUseCaseImplTest {

    private final String DEFAULT_DEVICE_ID = "e4db3a4c-a8a3-11ed-afa1-0242ac120002";

    private final String DEFAULT_SERVICE_ID = "f2c168ce-a8ae-11ed-afa1-0242ac120002";

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private UnassignServiceUseCaseImpl useCase;

    @Test()
    @DisplayName("When a new service is unassigned from a device, " +
            "it should be removed from the service list " +
            "and the service cost of the device should be updated correctly.")
    void shouldBeUnassigned() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service = Service.builder()
                .id(DEFAULT_SERVICE_ID)
                .name("TestService")
                .cost(5.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Device device = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(5.0)
                .serviceList(new ArrayList<>(List.of(service)))
                .build();

        final Device expected = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(0.0)
                .serviceList(List.of())
                .build();

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(device);
        when(deviceRepository.saveDevice(any(Device.class)))
                .thenReturn(expected);



        final Device result = useCase.unassign(DEFAULT_DEVICE_ID,DEFAULT_SERVICE_ID);
        assertEquals(expected,result);
    }

    @Test()
    @DisplayName("Should throw a DeviceNotFoundException exception when the device does not exist.")
    void shouldFailWhenDeviceNotFound() {

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(null);

        DeviceNotFoundException exception = assertThrows(
                DeviceNotFoundException.class, () -> useCase.unassign(DEFAULT_DEVICE_ID,DEFAULT_SERVICE_ID)
        );

        verify(deviceRepository, never()).saveDevice(any(Device.class));
        final String expected =
                "Device with id [e4db3a4c-a8a3-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}