package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.PaymentBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.core.entity.PaymentStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentGatewayImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentGatewayImpl paymentGateway;

    @Test
    void save_whenPaymentDoesNotExist_shouldSaveAndReturnPayment() throws PaymentCreationException {
        final var payment = createPayment();
        payment.setOrderId("123");
        final var ormPayment = PaymentBuilder.fromDomainToOrm(payment);
        final var savedPayment = PaymentBuilder.fromOrmToDomain(ormPayment);

        when(paymentRepository.findByOrderId(payment.getOrderId())).thenReturn(List.of());
        when(paymentRepository.save(any())).thenReturn(ormPayment);

        final var result = paymentGateway.save(payment);

        assertNotNull(result);
        assertEquals(savedPayment, result);

        verify(paymentRepository, times(1)).findByOrderId(payment.getOrderId());
        verify(paymentRepository, times(1)).save(any());
    }

    @Test
    void save_whenPaymentAlreadyExists_shouldThrowPaymentCreationException() {
        final var payment = createPayment();
        payment.setOrderId("123");

        final var paymentORM = PaymentBuilder.fromDomainToOrm(payment);

        when(paymentRepository.findByOrderId(payment.getOrderId())).thenReturn(List.of(paymentORM));

        final var exception = assertThrows(PaymentCreationException.class, () -> paymentGateway.save(payment));

        assertEquals(ExceptionCodes.PAYMENT_02_PAYMENT_CREATION, exception.getCode());
        assertEquals("Payment already exists for order with Id 123", exception.getMessage());

        verify(paymentRepository, times(1)).findByOrderId(payment.getOrderId());
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void updateStatus_whenPaymentNotFound_shouldThrowEntityNotFoundException() {
        final var paymentId = "123";

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.empty());

        final var exception = assertThrows(EntityNotFoundException.class, () -> paymentGateway.updateStatus(paymentId, PaymentStatus.APPROVED));

        assertEquals(ExceptionCodes.PAYMENT_01_NOT_FOUND, exception.getCode());
        assertEquals("Payment with id 123 was not found.", exception.getMessage());

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void updateStatus_whenPaymentStatusIsSame_shouldThrowPaymentCreationException() {
        final var paymentId = "abc123";

        final var paymentORM = PaymentBuilder.fromDomainToOrm(createPayment());
        paymentORM.setStatus(PaymentStatus.APPROVED.name());

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentORM));

        final var exception = assertThrows(PaymentCreationException.class, () -> paymentGateway.updateStatus(paymentId, PaymentStatus.APPROVED));

        assertEquals(ExceptionCodes.PAYMENT_O9_PAYMENT_CHARGE, exception.getCode());
        assertEquals("Payment abc123 is already with status APPROVED", exception.getMessage());

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, never()).save(any());
    }

    @Test
    void updateStatus_whenPaymentIsValid_shouldUpdateStatusAndReturnPayment() throws EntityNotFoundException, PaymentCreationException {
        final var paymentId = "123";
        final var payment = createPayment();
        payment.setStatus(PaymentStatus.PENDING);

        final var paymentORM = PaymentBuilder.fromDomainToOrm(payment);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(paymentORM));
        when(paymentRepository.save(paymentORM)).thenReturn(paymentORM);

        final var result = paymentGateway.updateStatus(paymentId, PaymentStatus.APPROVED);

        assertNotNull(result);
        assertEquals(PaymentStatus.APPROVED, result.getStatus());

        verify(paymentRepository, times(1)).findById(paymentId);
        verify(paymentRepository, times(1)).save(paymentORM);
    }

    @Test
    void findAll_whenNoPaymentsExist_shouldReturnEmptyList() {
        when(paymentRepository.findAll()).thenReturn(List.of());

        final var result = paymentGateway.findAll();

        assertTrue(result.isEmpty());

        verify(paymentRepository, times(1)).findAll();
    }

    @Test
    void findAll_whenPaymentsExist_shouldReturnListOfPayments() {
        final var payment = createPayment();
        final var ormPayment = PaymentBuilder.fromDomainToOrm(payment);
        final var ormPayments = List.of(ormPayment);

        when(paymentRepository.findAll()).thenReturn(ormPayments);

        final var result = paymentGateway.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());

        verify(paymentRepository, times(1)).findAll();
    }

    private Payment createPayment() {
        final var payment = new Payment();

        payment.setId("abc123");
        payment.setOrderId("order456");
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreatedAt(LocalDateTime.now());

        return payment;
    }
}
