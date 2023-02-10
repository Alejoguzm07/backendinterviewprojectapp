package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.endpoints;

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

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AddServiceITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should save a new service when is not duplicated.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/addService/addSuccessfully/deviceType.sql")
    })
    void addServiceSuccessfully() {

        final ServiceResponse response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File("src/test/resources/testcases/addService/addSuccessfully/request.json"))
                        .when().post("/rmm/v1/service")
                        .then().assertThat().statusCode(200)
                        .and().extract().body().as(ServiceResponse.class);

        final ServiceResponse expected = ServiceResponse.builder()
                .name("TestService")
                .acceptedDevices(List.of(
                        DeviceTypeResponse.builder()
                                .id("c3b18fbb-eeb2-4121-96f2-f076e7c8bb00")
                                .name("TestDeviceType")
                                .cost(4.0)
                                .build()
                ))
                .cost(2.0)
                .build();

        assertNotNull(response.getId());
        assertEquals(expected.getName(),response.getName());
        assertEquals(expected.getCost(),response.getCost());
        assertEquals(expected.getAcceptedDevices(),response.getAcceptedDevices());
    }

    @Test
    @DisplayName("Should not save a new device when is duplicated.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/addService/duplicatedService/service.sql")
    })

    void shouldFailWhenDuplicatedDevice() {

        final String response = given()
                .header("Content-type", "application/json")
                .and()
                .body(new File("src/test/resources/testcases/addService/duplicatedService/request.json"))
                .when().post("/rmm/v1/service")
                .then().assertThat().statusCode(400)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was duplicated: " +
                "Service with id [null] or name [TestService] is already registered";

        assertEquals(expected,response);
    }

}
