package com.ninjaone.backendinterviewproject.domain.model.device;

import com.ninjaone.backendinterviewproject.domain.model.service.Service;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DeviceTest {

    @Test()
    @DisplayName("Should calculate the total cost correctly including services cost and device cost depending on its device type.")
    void shouldCalculateTotalCosts() {

        final DeviceType deviceType = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service = Service.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Device device = Device.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(7.0)
                .serviceList(new ArrayList<>(List.of(service)))
                .build();

        assertEquals(11, device.calculateTotalCost());
    }

    @Test()
    @DisplayName("When a new service is assigned to a device, " +
            "it should be added to the service list " +
            "and the service cost of the device should be updated correctly.")
    void shouldIncrementCostsWhenServicesAreAssigned() {

        final DeviceType deviceType = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service1 = Service.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Service service2 = Service.builder()
                .id("388d3df6-a819-11ed-afa1-0242ac120002")
                .name("TestService2")
                .cost(3.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Device device = Device.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(7.0)
                .serviceList(new ArrayList<>(List.of(service1)))
                .build();

        device.assignService(service2);

        final List<Service> expectedServiceList = List.of(service1,service2);

        assertEquals(10.0, device.getServiceCost());
        assertEquals(expectedServiceList, device.getServiceList());
    }

    @Test()
    @DisplayName("When a new service is unassigned from a device, " +
            "it should be removed from the service list " +
            "and the service cost of the device should be updated correctly.")
    void shouldDecreaseCostsWhenServicesAreUnassigned() {

        final DeviceType deviceType = DeviceType.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final Service service1 = Service.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Service service2 = Service.builder()
                .id("388d3df6-a819-11ed-afa1-0242ac120002")
                .name("TestService2")
                .cost(3.0)
                .acceptedDevices(List.of(deviceType))
                .build();

        final Device device = Device.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice")
                .deviceType(deviceType)
                .serviceCost(10.0)
                .serviceList(new ArrayList<>(List.of(service1,service2)))
                .build();

        device.unassignService(service1.getId());

        final List<Service> expectedServiceList = List.of(service2);

        assertEquals(3.0, device.getServiceCost());
        assertEquals(expectedServiceList, device.getServiceList());
    }

}