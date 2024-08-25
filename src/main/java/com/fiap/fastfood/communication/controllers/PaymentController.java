package com.fiap.fastfood.communication.controllers;

import com.fiap.fastfood.common.builders.PaymentBuilder;
import com.fiap.fastfood.common.dto.response.PaymentResponse;
import com.fiap.fastfood.common.exceptions.model.ExceptionDetails;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentGateway gateway;
    private final PaymentUseCase useCase;

    public PaymentController(PaymentGateway paymentGateway, PaymentUseCase paymentUseCase) {
        this.gateway = paymentGateway;
        this.useCase = paymentUseCase;
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ExceptionDetails.class)))
    })
    @GetMapping(produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<PaymentResponse>> findAll() {
        final var result = useCase.findAll(gateway);

        return ResponseEntity.ok(result.stream()
                .map(PaymentBuilder::fromDomainToResponse)
                .toList());
    }
}
