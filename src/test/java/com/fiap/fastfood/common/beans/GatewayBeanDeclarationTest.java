package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GatewayBeanDeclarationTest {

    @InjectMocks
    private GatewayBeanDeclaration declaration;

    @Test
    void PaymentGatewayTest() {
        final var mock = Mockito.mock(PaymentRepository.class);

        final var result = declaration.paymentGateway(mock);

        Assertions.assertNotNull(result);
    }

    @Test
    void OrquestrationGatewayTest() {
        final var mock = Mockito.mock(PaymentUseCase.class);
        final var mockGateway = Mockito.mock(PaymentGateway.class);
        final var mockSender = Mockito.mock(MessageSender.class);

        final var result = declaration.orquestrationGateway(mock, mockGateway, mockSender);

        Assertions.assertNotNull(result);
    }


}
