package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedDeviceTypeException;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddDeviceTypeUseCaseImplTest {

    private final String DEFAULT_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private AddDeviceTypeUseCaseImpl useCase;

    @Test()
    @DisplayName("Should save a new device type when is not duplicated.")
    void shouldBeSaved() {

        final DeviceType deviceType = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final DeviceType expected = DeviceType.builder()
                .id("DEFAULT_ID")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        when(deviceTypeRepository.getDeviceByIdOrName(eq(deviceType.getId()), eq(deviceType.getName())))
                .thenReturn(null);
        when(deviceTypeRepository.add(any(DeviceType.class))).thenReturn(expected);
        when(idGenerator.generate()).thenReturn(DEFAULT_ID);

        final DeviceType result = useCase.add(deviceType);

        assertEquals(expected, result);
    }

    @Test()
    @DisplayName("Should throw a DuplicatedDeviceTypeException when the device type is duplicated.")
    void shouldFailWhenDuplicated() {
        final DeviceType deviceType = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        when(deviceTypeRepository.getDeviceByIdOrName(eq(deviceType.getId()), eq(deviceType.getName())))
                .thenReturn(deviceType);

        DuplicatedDeviceTypeException exception = assertThrows(DuplicatedDeviceTypeException.class, () -> useCase.add(deviceType));

        verify(idGenerator, never()).generate();
        verify(deviceTypeRepository, never()).add(any(DeviceType.class));
        final String expected =
                "Device type with id [c3b18fbb-eeb2-4121-96f2-f076e7c8bb00] or name [TestDeviceType] is already registered";
        assertEquals(expected, exception.getMessage());
    }

}