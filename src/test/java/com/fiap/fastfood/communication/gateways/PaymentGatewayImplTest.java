package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.external.orm.PaymentORM;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class PaymentGatewayImplTest {

    @InjectMocks
    PaymentGatewayImpl paymentGateway;

    @Mock
    PaymentRepository paymentRepository;

    @Test
    void saveTest() {
        final var paymentMock = Mockito.mock(Payment.class);
        final var paymentORMMock = Mockito.mock(PaymentORM.class);

        Mockito.when(paymentRepository.save(any()))
                .thenReturn(paymentORMMock);

        Assertions.assertNotNull(
                paymentGateway.save(paymentMock)
        );
    }

    @Test
    void findAllTest() {
        final var paymentORMMock = Mockito.mock(PaymentORM.class);

        Mockito.when(paymentRepository.findAll())
                .thenReturn(List.of(paymentORMMock));

        final var result = paymentGateway.findAll();

        Assertions.assertFalse(result.isEmpty());
    }

}
