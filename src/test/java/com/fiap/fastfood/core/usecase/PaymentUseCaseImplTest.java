package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.Payment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class PaymentUseCaseImplTest {

    @InjectMocks
    private PaymentUseCaseImpl paymentUseCase;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private OrquestrationGateway orquestrationGateway;

    @Test
    void submitTest() throws PaymentCreationException {
        final var paymentMock = Mockito.mock(Payment.class);
        TransactionInformationStorage.putReceiveCount("2");

        Mockito.when(paymentGateway.save(paymentMock))
                .thenReturn(paymentMock);

        Assertions.assertNotNull(paymentUseCase.createPayment(paymentMock, paymentGateway, orquestrationGateway));
    }

    @Test
    void findAllTest() {
        final var paymentMock = Mockito.mock(Payment.class);
        TransactionInformationStorage.putReceiveCount("2");

        Mockito.when(paymentGateway.findAll())
                .thenReturn(List.of(paymentMock));

        Assertions.assertNotNull(paymentUseCase.findAll(paymentGateway));
    }
}
