package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller;

import com.ninjaone.backendinterviewproject.domain.model.device.Device;
import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import com.ninjaone.backendinterviewproject.domain.usecases.impl.*;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.cache.impl.RmmCacheManagerImpl;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.mapper.impl.DeviceMapperImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceControllerCacheTest {

    private final String DEFAULT_TYPE_ID = "add4a136-a895-11ed-afa1-0242ac120002";
    private final String DEFAULT_DEVICE_ID = "a331824a-a89e-11ed-afa1-0242ac120002";
    private final String DEFAULT_SERVICE_ID = "f2c168ce-a8ae-11ed-afa1-0242ac120002";

    @Mock
    private RmmCacheManagerImpl cacheManager;

    @Mock
    private CalculateDeviceServiceCostUseCaseImpl calculateDeviceServiceCostUseCase;

    @Mock
    private AssignServiceUseCaseImpl assignServiceUseCase;

    @Mock
    private UnassignServiceUseCaseImpl unassignServiceUseCase;

    @Mock
    private DeviceMapperImpl deviceMapper;

    @InjectMocks
    private DeviceController controller;

    @Test()
    @DisplayName("Should save the service cost calculation result in the cache when it is consulted a first time," +
            "and return the calculation from the cache when it is consulted a second time.")
    void shouldPersistTheResult() {

        //When the cache is asked the first time to retrieve the value
        when(cacheManager.get(eq(DEFAULT_DEVICE_ID))).thenReturn(null);

        //When the service calculates the first time
        when(calculateDeviceServiceCostUseCase.calculate(eq(DEFAULT_DEVICE_ID))).thenReturn(7.0);

        //When the value is saved in the cache
        doNothing().when(cacheManager).put(eq(DEFAULT_DEVICE_ID),eq(7.0));

        //First calculation request
        controller.calculateServicesTotal(DEFAULT_DEVICE_ID);

        //When the cache is asked the second time to retrieve the value
        when(cacheManager.get(eq(DEFAULT_DEVICE_ID))).thenReturn(7.0);

        //Second calculation request
        controller.calculateServicesTotal(DEFAULT_DEVICE_ID);

        verify(calculateDeviceServiceCostUseCase,times(1)).calculate(eq(DEFAULT_DEVICE_ID));
        verify(cacheManager,times(2)).get(eq(DEFAULT_DEVICE_ID));
    }

    @Test()
    @DisplayName("Should update the cache when a service is assigned to the device.")
    void shouldUpdateTheCacheWhenServiceIsAssigned() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service = Service.builder()
                .id(DEFAULT_SERVICE_ID)
                .name("TestService")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Device device = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceList(List.of(service))
                .serviceCost(7.0)
                .build();

        when(assignServiceUseCase.assign(eq(DEFAULT_DEVICE_ID),eq(DEFAULT_SERVICE_ID)))
                .thenReturn(device);

        doNothing().when(cacheManager).put(eq(DEFAULT_DEVICE_ID),eq(7.0));

        controller.assignServiceToDevice(DEFAULT_DEVICE_ID,DEFAULT_SERVICE_ID);

        verify(cacheManager,times(1)).put(eq(DEFAULT_DEVICE_ID),eq(7.0));

    }

    @Test()
    @DisplayName("Should update the cache when a service is unassigned to the device.")
    void shouldUpdateTheCacheWhenServiceIsUnassigned() {

        final DeviceType deviceType = DeviceType.builder()
                .id(DEFAULT_TYPE_ID)
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Device device = Device.builder()
                .id(DEFAULT_DEVICE_ID)
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceList(List.of())
                .serviceCost(0.0)
                .build();

        when(unassignServiceUseCase.unassign(eq(DEFAULT_DEVICE_ID),eq(DEFAULT_SERVICE_ID)))
                .thenReturn(device);

        doNothing().when(cacheManager).put(eq(DEFAULT_DEVICE_ID),eq(0.0));

        controller.unassignServiceToDevice(DEFAULT_DEVICE_ID,DEFAULT_SERVICE_ID);

        verify(cacheManager,times(1)).put(eq(DEFAULT_DEVICE_ID),eq(0.0));

    }


}