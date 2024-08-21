package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomMessageHeaders;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.MessageCreationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import com.fiap.fastfood.core.entity.Payment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.MessageHeaders;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrquestrationGatewayImplTest {

    @InjectMocks
    private OrquestrationGatewayImpl orquestrationGateway;

    @Mock
    private PaymentUseCase paymentUseCase;

    @Mock
    private PaymentGateway paymentGateway;

    @Mock
    private MessageSender messageSender;

    @Test
    void listenToPaymentCreation_success() throws PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        orquestrationGateway.listenToPaymentCreation(headers, message);

        verify(paymentUseCase, times(1)).createPayment(any(Payment.class), eq(paymentGateway), eq(orquestrationGateway));
        verifyNoMoreInteractions(paymentUseCase);
    }

    @Test
    void listenToPaymentCreation_whenPaymentCreationFails_shouldThrowPaymentCreationException() throws PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        doThrow(new RuntimeException("Simulated Failure")).when(paymentUseCase).createPayment(any(), any(), any());

        assertThrows(PaymentCreationException.class, () -> orquestrationGateway.listenToPaymentCreation(headers, message));

        verify(paymentUseCase, times(1)).createPayment(any(Payment.class), any(PaymentGateway.class), any(OrquestrationGatewayImpl.class));
    }

    @Test
    void listenToPaymentCharge_success() throws PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        orquestrationGateway.listenToPaymentCharge(headers, message);

        verify(paymentUseCase, times(1)).chargePayment(any(Payment.class), eq(paymentGateway), eq(orquestrationGateway));
        verifyNoMoreInteractions(paymentUseCase);
    }

    @Test
    void listenToPaymentCharge_whenPaymentChargeFails_shouldThrowPaymentCreationException() throws PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        doThrow(new RuntimeException("Simulated Failure")).when(paymentUseCase).chargePayment(any(), any(), any());

        assertThrows(PaymentCreationException.class, () -> orquestrationGateway.listenToPaymentCharge(headers, message));

        verify(paymentUseCase, times(1)).chargePayment(any(Payment.class), any(PaymentGateway.class), any(OrquestrationGatewayImpl.class));
    }

    @Test
    void listenToPaymentReversal_success() throws PaymentCancellationException, PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        orquestrationGateway.listenToPaymentReversal(headers, message);

        verify(paymentUseCase, times(1)).reversePayment(any(Payment.class), eq(paymentGateway), eq(orquestrationGateway));
        verifyNoMoreInteractions(paymentUseCase);
    }

    @Test
    void listenToPaymentReversal_whenPaymentReversalFails_shouldThrowPaymentCancellationException() throws PaymentCancellationException, PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        doThrow(new RuntimeException("Simulated Failure")).when(paymentUseCase).reversePayment(any(), any(), any());

        assertThrows(PaymentCancellationException.class, () -> orquestrationGateway.listenToPaymentReversal(headers, message));

        verify(paymentUseCase, times(1)).reversePayment(any(Payment.class), any(PaymentGateway.class), any(OrquestrationGatewayImpl.class));
    }

    @Test
    void listenToPaymentCancellation_success() throws PaymentCancellationException, PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        orquestrationGateway.listenToPaymentCancellation(headers, message);

        verify(paymentUseCase, times(1)).cancelPayment(any(Payment.class), eq(paymentGateway), eq(orquestrationGateway));
        verifyNoMoreInteractions(paymentUseCase);
    }

    @Test
    void listenToPaymentCancellation_whenPaymentCancellationFails_shouldThrowPaymentCancellationException() throws PaymentCancellationException, PaymentCreationException {
        final var headers = new MessageHeaders(null);
        final var message = createMessage();

        doThrow(new RuntimeException("Simulated Failure")).when(paymentUseCase).cancelPayment(any(), any(), any());

        assertThrows(PaymentCancellationException.class, () -> orquestrationGateway.listenToPaymentCancellation(headers, message));

        verify(paymentUseCase, times(1)).cancelPayment(any(Payment.class), any(PaymentGateway.class), any(OrquestrationGatewayImpl.class));
    }

    @Test
    void sendResponse_success() throws PaymentCreationException, MessageCreationException {
        final var orderId = "order123";
        final var customerId = 1L;
        final var paymentId = "payment123";
        final var stepEnum = OrquestrationStepEnum.CHARGE_PAYMENT;

        orquestrationGateway.sendResponse(orderId, customerId, paymentId, stepEnum, true);

        verify(messageSender, times(1)).sendMessage(any(), any(), any());
        verifyNoMoreInteractions(messageSender);
    }


    @Test
    void sendResponse_whenMessageSendingFails_shouldThrowPaymentCreationException() throws MessageCreationException {
        final var orderId = "order123";
        final var customerId = 1L;
        final var paymentId = "payment123";
        final var stepEnum = OrquestrationStepEnum.CHARGE_PAYMENT;

        doThrow(new RuntimeException("Simulated Failure")).when(messageSender).sendMessage(any(), anyString(), anyString());

        assertThrows(PaymentCreationException.class, () -> orquestrationGateway.sendResponse(orderId, customerId, paymentId, stepEnum, true));

        verify(messageSender, times(1)).sendMessage(any(), any(), any());
    }

    private OrderCommand orderCommand() {
        return new OrderCommand("orderId", 1L, "paymentId");
    }

    private CustomQueueMessage<OrderCommand> createMessage() {
        return new CustomQueueMessage<>(
                new CustomMessageHeaders("sagaId", "orderId", "messageType", "order"),
                orderCommand());
    }
}
