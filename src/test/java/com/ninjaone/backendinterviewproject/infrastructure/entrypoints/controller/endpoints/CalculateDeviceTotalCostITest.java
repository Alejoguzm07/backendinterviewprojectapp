package com.ninjaone.backendinterviewproject.infrastructure.entrypoints.controller.endpoints;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CalculateDeviceTotalCostITest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should calculate correctly the sum of the costs of the services assigned to a device and the device cost.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/calculateDeviceTotalCost/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/calculateDeviceTotalCost/device.sql"),
            @Sql(scripts = "classpath:testcases/calculateDeviceTotalCost/service.sql"),
            @Sql(scripts = "classpath:testcases/calculateDeviceTotalCost/compatibleDeviceTypes.sql"),
            @Sql(scripts = "classpath:testcases/calculateDeviceTotalCost/assignedServices.sql")
    })
    void shouldCalculate() {
        final Double response = given()
                .when().get("/rmm/v1/device/calculate-total/7e506008-a818-11ed-afa1-0242ac120002")
                .then().assertThat().statusCode(200)
                .and().extract().body().as(Double.class);

        assertEquals(11.0,response);

    }

    @Test
    @DisplayName("Should fail when calculating when the device is not found.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql")
    })
    void shouldFailWhenNoDevice() {
        final String response = given()
                .when().get("/rmm/v1/device/calculate-total/7e506008-a818-11ed-afa1-0242ac120002")
                .then().assertThat().statusCode(404)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was not found: " +
                "Device with id [7e506008-a818-11ed-afa1-0242ac120002] does not exist";

        assertEquals(expected,response);
    }

}
