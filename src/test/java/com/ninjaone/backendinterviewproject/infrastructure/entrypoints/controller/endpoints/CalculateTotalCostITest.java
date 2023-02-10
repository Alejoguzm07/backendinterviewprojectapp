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
public class CalculateTotalCostITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should calculate correctly the sum of the costs of the services assigned to all devices " +
            "and sum of the devices cost.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/calculateTotalCost/deviceType.sql"),
            @Sql(scripts = "classpath:testcases/calculateTotalCost/device.sql"),
            @Sql(scripts = "classpath:testcases/calculateTotalCost/service.sql"),
            @Sql(scripts = "classpath:testcases/calculateTotalCost/compatibleDeviceTypes.sql"),
            @Sql(scripts = "classpath:testcases/calculateTotalCost/assignedServices.sql")
    })
    void shouldCalculate() {
        final Double response = given()
                .when().get("/rmm/v1/device/calculate-total")
                .then().assertThat().statusCode(200)
                .and().extract().body().as(Double.class);

        assertEquals(64.0,response);

    }
}
