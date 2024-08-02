package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.dto.request.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentControllerTest {

    @LocalServerPort
    private int port;

    @Test
    void givenPaymentRequestThenRespondWithStatusCreated() {
        final var paymentRequest = new PaymentRequest("orderId");

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .body(paymentRequest)
                .when()
                .post("/Payment")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.CREATED.value())
                .contentType(JSON);
    }

    @Test
    void givenGetThenRespondWithPaymentlistAndStatusOK() {

        given()
                .port(port)
                .header("Content-Type", "application/json")
                .when()
                .get("/Payment")
                .then()
                .log().ifValidationFails()
                .statusCode(HttpStatus.OK.value())
                .contentType(JSON);
    }

}
