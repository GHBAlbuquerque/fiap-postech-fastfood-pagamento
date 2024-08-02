package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaymentUseCaseImplTest {

    @InjectMocks
    private PaymentUseCaseImpl paymentUseCase;

    @Test
    void submitTest() {
        final var gatewayMock = Mockito.mock(PaymentGateway.class);
        final var paymentMock = Mockito.mock(Payment.class);

        Mockito.when(gatewayMock.save(paymentMock))
                .thenReturn(paymentMock);

        Assertions.assertNotNull(paymentUseCase.submit(paymentMock, gatewayMock));
    }

    @Test
    void findAllTest() {
        final var gatewayMock = Mockito.mock(PaymentGateway.class);
        final var paymentMock = Mockito.mock(Payment.class);

        Mockito.when(gatewayMock.findAll())
                .thenReturn(List.of(paymentMock));

        Assertions.assertNotNull(paymentUseCase.findAll(gatewayMock));
    }
}
