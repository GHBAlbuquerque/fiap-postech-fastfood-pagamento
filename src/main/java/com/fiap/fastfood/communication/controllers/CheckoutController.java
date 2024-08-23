package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.PaymentBuilder;
import com.fiap.fastfood.common.dto.request.CheckoutRequest;
import com.fiap.fastfood.common.dto.request.PaymentRequest;
import com.fiap.fastfood.common.dto.response.PaymentResponse;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;


@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final PaymentGateway gateway;
    private final PaymentUseCase useCase;
    private final OrquestrationGateway orquestrationGateway;

    public CheckoutController(PaymentGateway checkoutGateway, PaymentUseCase checkoutUseCase, OrquestrationGateway orquestrationGateway) {
        this.gateway = checkoutGateway;
        this.useCase = checkoutUseCase;
        this.orquestrationGateway = orquestrationGateway;
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody @Valid PaymentRequest request) throws PaymentCreationException {
        final var payment = PaymentBuilder.fromRequestToDomain(request);

        final var result = useCase.createPayment(payment, gateway, orquestrationGateway);

        return ResponseEntity
                .created(URI.create(result.getId()))
                .body(
                        PaymentBuilder.fromDomainToResponse(result)
                );
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @PostMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<PaymentResponse> checkout(@RequestBody @Valid CheckoutRequest request) throws PaymentCreationException {
        final var payment = PaymentBuilder.fromCheckoutRequestToDomain(request);

        final var result = useCase.createPayment(payment, gateway, orquestrationGateway);

        return ResponseEntity
                .created(URI.create(result.getId()))
                .body(
                        PaymentBuilder.fromDomainToResponse(result)
                );
    }
}
