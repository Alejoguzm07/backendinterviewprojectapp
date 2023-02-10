package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.endpoints;

import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceResponse;
import com.ninjaone.backendinterviewproject.infrastructure.entrypoints.dto.DeviceTypeResponse;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddDeviceITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should save a new device when is not duplicated.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/addDevice/addSuccessfully/deviceType.sql")
    })
    void addDeviceSuccessfully() {

        final DeviceResponse response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File("src/test/resources/testcases/addDevice/addSuccessfully/request.json"))
                        .when().post("/rmm/v1/device")
                        .then().assertThat().statusCode(200)
                        .and().extract().body().as(DeviceResponse.class);

        final DeviceResponse expected = DeviceResponse.builder()
                .name("TestDevice")
                .deviceType(
                        DeviceTypeResponse.builder()
                                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                                .name("TestDeviceType")
                                .cost(4.0)
                                .build()
                )
                .serviceList(List.of())
                .build();

        assertNotNull(response.getId());
        assertEquals(expected.getName(),response.getName());
        assertEquals(expected.getDeviceType(),response.getDeviceType());
        assertEquals(expected.getServiceList(),response.getServiceList());
    }

    @Test
    @DisplayName("Should not save a new device when is duplicated.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/addDevice/duplicatedDevice/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/addDevice/duplicatedDevice/device.sql")
    })

    void shouldFailWhenDuplicatedDevice() {

        final String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File("src/test/resources/testcases/addDevice/duplicatedDevice/request.json"))
                .when().post("/rmm/v1/device")
                .then().assertThat().statusCode(400)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was duplicated: " +
                "Device with id [null] or name [TestDevice] is already registered";

        assertEquals(expected,response);
    }

    @Test
    @DisplayName("Should not save a new device when the device type does not exist.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql")
    })

    void shouldFailWhenNoDeviceTypeFound() {

        final String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File("src/test/resources/testcases/addDevice/noDeviceType/request.json"))
                .when().post("/rmm/v1/device")
                .then().assertThat().statusCode(404)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was not found: " +
                "Device type with id [c3b18fbb-eeb2-4121-96f2-f076e7c8bb00] does not exist";

        assertEquals(expected,response);
    }

}
