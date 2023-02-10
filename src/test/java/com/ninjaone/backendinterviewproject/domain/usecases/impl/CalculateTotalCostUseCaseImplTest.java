package com.ninjaone.backendinterviewproject.domain.usecases.impl;

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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CalculateTotalCostUseCaseImplTest {

    @Mock
    private DeviceRepository deviceRepository;

    @InjectMocks
    private CalculateTotalCostUseCaseImpl useCase;

    @Test()
    @DisplayName("Should calculate correctly the sum of the costs of the services assigned to all devices " +
            "and sum of the devices cost.")
    void shouldCalculate() {

        final DeviceType deviceType1 = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType1")
                .cost(4.0)
                .build();
        final DeviceType deviceType2 = DeviceType.builder()
                .id("4bb72b22-a818-11ed-afa1-0242ac120002")
                .name("TestDeviceType2")
                .cost(4.0)
                .build();


        final Service service1 = Service.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType1))
                .build();
        final Service service2 = Service.builder()
                .id("2d73b72e-a819-11ed-afa1-0242ac120002")
                .name("TestService2")
                .cost(5.0)
                .acceptedDevices(List.of(deviceType2))
                .build();
        final Service service3 = Service.builder()
                .id("388d3df6-a819-11ed-afa1-0242ac120002")
                .name("TestService3")
                .cost(3.0)
                .acceptedDevices(List.of(deviceType1,deviceType2))
                .build();
        final Service service4 = Service.builder()
                .id("3e24e2f0-a819-11ed-afa1-0242ac120002")
                .name("TestService4")
                .cost(1.0)
                .acceptedDevices(List.of(deviceType1,deviceType2))
                .build();


        final Device device1 = Device.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice1")
                .deviceType(deviceType1)
                .serviceCost(7.0)
                .serviceList(List.of(service1))
                .build();
        final Device device2 = Device.builder()
                .id("adb4ca00-a818-11ed-afa1-0242ac120002")
                .name("TestDevice2")
                .deviceType(deviceType1)
                .serviceCost(11.0)
                .serviceList(List.of(service1,service3,service4))
                .build();
        final Device device3 = Device.builder()
                .id("b4286d42-a818-11ed-afa1-0242ac120002")
                .name("TestDevice3")
                .deviceType(deviceType1)
                .serviceCost(11.0)
                .serviceList(List.of(service1,service3,service4))
                .build();
        final Device device4 = Device.builder()
                .id("b9d5dd1a-a818-11ed-afa1-0242ac120002")
                .name("TestDevice4")
                .deviceType(deviceType2)
                .serviceCost(9.0)
                .serviceList(List.of(service2,service3,service4))
                .build();
        final Device device5 = Device.builder()
                .id("bec6a2c8-a818-11ed-afa1-0242ac120002")
                .name("TestDevice5")
                .deviceType(deviceType2)
                .serviceCost(6.0)
                .serviceList(List.of(service2,service4))
                .build();


        when(deviceRepository.getAllDevices())
                .thenReturn(List.of(device1,device2,device3,device4,device5));

        assertEquals(64.0,useCase.calculate());

    }

}