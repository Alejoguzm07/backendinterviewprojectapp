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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CalculateDeviceServiceCostUseCaseImplTest {

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";
    private final String DEFAULT_DEVICE_ID = "a331824a-a89e-11ed-afa1-0242ac120002";
    private final String DEFAULT_SERVICE_ID = "f2c168ce-a8ae-11ed-afa1-0242ac120002";

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private CalculateDeviceServiceCostUseCaseImpl useCase;

    @Test()
    @DisplayName("Should calculate correctly the sum of the costs of the services assigned to a device.")
    void shouldCalculate() {

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
                .serviceList(List.of(service))
                .build();

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(device);

        assertEquals(5.0,useCase.calculate(DEFAULT_DEVICE_ID));

    }

    @Test()
    @DisplayName("Should throw a DeviceNotFound exception when the device does not exist.")
    void shouldFailWhenCalculating() {

        when(deviceRepository.getDeviceByID(eq(DEFAULT_DEVICE_ID)))
                .thenReturn(null);

        DeviceNotFoundException exception = assertThrows(DeviceNotFoundException.class, () -> useCase.calculate(DEFAULT_DEVICE_ID));

        final String expected =
                "Device with id [a331824a-a89e-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}