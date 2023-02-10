package com.ninjaone.backendinterviewproject.domain.model.service;

import com.ninjaone.backendinterviewproject.domain.model.device.DeviceType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ServiceTest {

    @Test()
    @DisplayName("Should respond positively when a service is compatible with a device type.")
    void shouldBeAssignable() {

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

        assertTrue(service.canBeAssigned(deviceType));
    }

    @Test()
    @DisplayName("Should respond negatively when a service is not compatible with a device type.")
    void shouldBeUnassignable() {

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

        final Service service = Service.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService1")
                .cost(7.0)
                .acceptedDevices(List.of(deviceType1))
                .build();

        assertFalse(service.canBeAssigned(deviceType2));
    }

}