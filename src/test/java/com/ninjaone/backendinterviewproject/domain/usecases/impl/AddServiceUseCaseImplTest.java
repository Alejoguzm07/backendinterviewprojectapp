package com.ninjaone.backendinterviewproject.domain.usecases.impl;

import com.ninjaone.backendinterviewproject.domain.exception.DuplicatedServiceException;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.DeviceTypeRepository;
import com.ninjaone.backendinterviewproject.domain.usecases.repository.ServiceRepository;
import com.ninjaone.backendinterviewproject.domain.utils.IdGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AddServiceUseCaseImplTest {
    private final String DEFAULT_SERVICE_ID = "e4db3a4c-a8a3-11ed-afa1-0242ac120002";

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";

    @Mock
    private ServiceRepository serviceRepository;

    @Mock
    private DeviceTypeRepository deviceTypeRepository;

    @Mock
    private IdGenerator idGenerator;

    @InjectMocks
    private AddServiceUseCaseImpl useCase;

    @Test()
    @DisplayName("Should save a new service when is not duplicated.")
    void shouldBeSaved() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service = Service.builder()
                .id("d7d04954-a8a4-11ed-afa1-0242ac120002")
                .name("TestService")
                .cost(5.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Service expected = Service.builder()
                .id("DEFAULT_SERVICE_ID")
                .name("TestService")
                .cost(5.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        when(serviceRepository.getServiceByIdOrName(eq(service.getId()), eq(service.getName())))
                .thenReturn(null);
        when(deviceTypeRepository.getAllByIdList(eq(List.of(DEFAULT_TYPE_ID))))
                .thenReturn(List.of(deviceType));
        when(idGenerator.generate()).thenReturn(DEFAULT_SERVICE_ID);
        when(serviceRepository.saveService(any(Service.class)))
                .thenReturn(expected);

        final Service result = useCase.add(service);

        assertEquals(expected, result);

    }

    @Test()
    @DisplayName("Should throw a DuplicatedServiceException when the service is duplicated.")
    void shouldFailWhenDuplicated() {
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

        when(serviceRepository.getServiceByIdOrName(eq(service.getId()), eq(service.getName())))
                .thenReturn(service);

        DuplicatedServiceException exception = assertThrows(DuplicatedServiceException.class, () -> useCase.add(service));

        verify(idGenerator, never()).generate();
        verify(serviceRepository , never()).saveService(any(Service.class));
        verify(deviceTypeRepository , never()).getAllByIdList(anyList());

        final String expected =
                "Service with id [e4db3a4c-a8a3-11ed-afa1-0242ac120002] or name [TestService] is already registered";
        assertEquals(expected, exception.getMessage());
    }
}