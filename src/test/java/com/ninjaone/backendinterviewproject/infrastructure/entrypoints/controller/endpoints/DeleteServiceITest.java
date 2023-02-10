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
public class DeleteServiceITest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("Should delete a device when it exists.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql"),
            @Sql(scripts = "classpath:testcases/deleteService/service.sql")
    })
    void deleteDeviceSuccessfully() {

        given()
                .when().delete("/rmm/v1/service/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(200);
    }

    @Test
    @DisplayName("Should not delete a service when it does not exist.")
    @SqlGroup({
            @Sql(scripts = "classpath:testcases/common/clean.sql")
    })
    void shouldFailWhenNoService() {

        final String response = given()
                .when().delete("/rmm/v1/service/e79ae5d3-e57f-4deb-983a-45f78288dee0")
                .then().assertThat().statusCode(404)
                .and().extract().body().asString();

        final String expected = "There was a problem with your request, an element was not found: " +
                "Service with id [e79ae5d3-e57f-4deb-983a-45f78288dee0] does not exist";

        assertEquals(expected,response);
    }
}
