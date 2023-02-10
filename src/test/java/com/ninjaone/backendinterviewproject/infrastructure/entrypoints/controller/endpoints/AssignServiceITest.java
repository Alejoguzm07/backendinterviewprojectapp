package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.endpoints;

import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.ServiceResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AssignServiceITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should assign a service to a device successfully when both device and service exist, " +
            "and when the service is compatible with the type of the device.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/assignService/assignSuccessfully/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/assignService/assignSuccessfully/device.sql"),
            @Sql(scripts = "classpath:testcases/assignService/assignSuccessfully/service.sql"),
            @Sql(scripts = "classpath:testcases/assignService/assignSuccessfully/compatibleDeviceTypes.sql")
    })
    void assignServiceSuccessfully() {

        final DeviceResponse response = given()
                .header("Content-type", "application/json")
                .and()
                .when().post("/rmm/v1/device/assign/device-id/7e506008-a818-11ed-afa1-0242ac120002/service-id/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(200)
                .and().extract().body().as(DeviceResponse.class);

        final DeviceTypeResponse expectedType = DeviceTypeResponse.builder()
                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                .name("TestDeviceType")
                .cost(4.0)
                .build();

        final ServiceResponse expectedService = ServiceResponse.builder()
                .id("e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .name("TestService")
                .cost(7.0)
                .acceptedDevices(List.of(expectedType))
                .build();

        final DeviceResponse expected = DeviceResponse.builder()
                .id("7e506008-a818-11ed-afa1-0242ac120002")
                .name("TestDevice")
                .deviceType(expectedType)
                .serviceList(List.of(expectedService))
                .build();

        assertEquals(expected,response);
    }

    @Test
    @DisplayName("Should not assign a service to a device when the device does not exist.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql")
    })
    void shouldFailWhenNoDevice() {
        final String response = given()
                .when().post("/rmm/v1/device/assign/device-id/7e506008-a818-11ed-afa1-0242ac120002/service-id/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(404)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was not found: " +
                "Device with id [7e506008-a818-11ed-afa1-0242ac120002] does not exist";

        assertEquals(expected,response);
    }

    @Test
    @DisplayName("Should not assign a service to a device when the service does not exist.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/assignService/noService/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/assignService/noService/device.sql")
    })
    void shouldFailWhenNoService() {
        final String response = given()
                .when().post("/rmm/v1/device/assign/device-id/7e506008-a818-11ed-afa1-0242ac120002/service-id/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(404)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was not found: " +
                "Service with id [e79ae5d3-e57f-4deb-983a-45f78288dee0] does not exist";

        assertEquals(expected,response);
    }

    @Test
    @DisplayName("Should not assign a service to a device when the service is not compatible with the type of the device.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/assignService/uncompatibleDeviceType/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/assignService/uncompatibleDeviceType/device.sql"),
            @Sql(scripts = "classpath:testcases/assignService/uncompatibleDeviceType/service.sql"),
    })
    void shouldFailWhenUncompatibleDeviceType() {
        final String response = given()
                .when().post("/rmm/v1/device/assign/device-id/7e506008-a818-11ed-afa1-0242ac120002/service-id/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(400)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request: " +
                "Service with id [e79ae5d3-e57f-4deb-983a-45f78288dee0] can not be assigned to " +
                "devices of type with id [c3b18fbb-eeb2-4121-96f2-f076e7c8bb00]";

        assertEquals(expected,response);
    }

}
