package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DeviceTypeNotFound;
import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedDeviceException;
import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddDeviceUseCaseImplTest {

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";
    private final String DEFAULT_DEVICE_ID = "a331824a-a89e-11ed-afa1-0242ac120002";

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private DeviceRepository deviceRepository;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private AddDeviceUseCaseImpl useCase;

    @Test()
    @DisplayName("Should save a new device when is not duplicated and its device type exists.")
    void shouldBeSaved() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Device expected = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(7.0)
                .serviceList(new ArrayList<>())
                .build();

        when(deviceTypeRepository.getDeviceTypeByID(eq(deviceType.getId())))
                .thenReturn(deviceType);
        when(deviceRepository.saveDevice(any(Device.class))).thenReturn(expected);
        when(idGenerator.generate()).thenReturn(DEFAULT_DEVICE_ID);

        final Device device = Device.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(7.0)
                .serviceList(new ArrayList<>())
                .build();

        final Device result = useCase.add(device);

        assertEquals(expected, result);

    }

    @Test()
    @DisplayName("Should throw a DuplicatedDeviceException when the device is duplicated.")
    void shouldFailWhenDuplicated() {
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

        when(deviceRepository.getDeviceByIdOrName(eq(device.getId()), eq(device.getName())))
                .thenReturn(device);

        DuplicatedDeviceException exception = assertThrows(DuplicatedDeviceException.class, () -> useCase.add(device));

        verify(idGenerator, never()).generate();
        verify(deviceTypeRepository, never()).getDeviceTypeByID(anyString());
        verify(deviceRepository, never()).saveDevice(any(Device.class));

        final String expected =
                "Device with id [a331824a-a89e-11ed-afa1-0242ac120002] or name [TestDevice] is already registered";
        assertEquals(expected, exception.getMessage());
    }

    @Test()
    @DisplayName("Should throw a DeviceTypeNotFound when the device type does not exist.")
    void shouldFailWhenInvalidType() {
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

        when(deviceRepository.getDeviceByIdOrName(eq(device.getId()), eq(device.getName())))
                .thenReturn(null);
        when(deviceTypeRepository.getDeviceTypeByID(eq(DEFAULT_TYPE_ID)))
                .thenReturn(null);

        DeviceTypeNotFound exception = assertThrows(DeviceTypeNotFound.class, () -> useCase.add(device));

        verify(idGenerator, never()).generate();
        verify(deviceRepository, never()).saveDevice(any(Device.class));

        final String expected =
                "Device type with id [add4a136-a895-11ed-afa1-0242ac120002] does not exist";
        assertEquals(expected, exception.getMessage());
    }

}