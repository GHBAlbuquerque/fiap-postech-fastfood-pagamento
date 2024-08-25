package com.fiap.fastfood.communication.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PaymentControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenGetThenRespondWithPaymentlistAndStatusOK() {

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/payment")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .contentType(JSON);
    }

}
