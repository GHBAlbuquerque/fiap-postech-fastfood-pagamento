package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import org.springframework.messaging.MessageHeaders;

public interface OrquestrationGateway {

    void listenToPaymentCreation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCreationException;

    void listenToPaymentCharge(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCreationException;

    void listenToPaymentReverse(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCancellationException;

    void listenToPaymentCancellation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCancellationException;


    void sendResponse(String orderId,
                      String customerId,
                      String paymentId,
                      OrquestrationStepEnum orquestrationStepEnum,
                      Boolean stepSuccessful) throws PaymentCreationException;
}
