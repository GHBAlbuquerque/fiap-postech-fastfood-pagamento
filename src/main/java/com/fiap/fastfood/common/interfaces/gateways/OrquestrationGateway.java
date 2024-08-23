package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.PaymentCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.springframework.messaging.MessageHeaders;

public interface OrquestrationGateway {

    void listenToPaymentCreation(MessageHeaders headers, CustomQueueMessage<PaymentCommand> message) throws PaymentCreationException;

    void listenToPaymentCharge(MessageHeaders headers, CustomQueueMessage<PaymentCommand> message) throws PaymentCreationException;

    void listenToPaymentReversal(MessageHeaders headers, CustomQueueMessage<PaymentCommand> message) throws PaymentCancellationException;

    void listenToPaymentCancellation(MessageHeaders headers, CustomQueueMessage<PaymentCommand> message) throws PaymentCancellationException;

    void sendResponse(String orderId,
                      Long customerId,
                      String paymentId,
                      OrquestrationStepEnum orquestrationStepEnum,
                      Boolean stepSuccessful) throws PaymentCreationException;
}
