package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.dto.command.OrderCommand;
import com.fiap.fastfood.common.dto.message.CustomQueueMessage;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.core.entity.PaymentStatus;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.MessageHeaders;

import static com.fiap.fastfood.common.logging.Constants.HEADER_RECEIVE_COUNT;
import static io.awspring.cloud.sqs.annotation.SqsListenerAcknowledgementMode.ON_SUCCESS;

public class OrquestrationGatewayImpl implements OrquestrationGateway {

    private final PaymentUseCase paymentUseCase;
    private final PaymentGateway paymentGateway;
    private final MessageSender messageSender;

    private static final Logger logger = LogManager.getLogger(OrquestrationGatewayImpl.class);

    @Value("${aws.queue_resposta_criar_pedido.url}")
    private String queueResponseSaga;

    public OrquestrationGatewayImpl(PaymentUseCase paymentUseCase, PaymentGateway paymentGateway, MessageSender messageSender) {
        this.paymentUseCase = paymentUseCase;
        this.paymentGateway = paymentGateway;
        this.messageSender = messageSender;
    }

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_solicitar_pagamento.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1", acknowledgementMode = ON_SUCCESS)
    public void listenToPaymentCreation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var newPayment = new Payment(message.getBody().getOrderId(), PaymentStatus.PENDING);

            paymentUseCase.createPayment(
                    newPayment,
                    paymentGateway,
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new PaymentCreationException(ExceptionCodes.PAYMENT_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    @SqsListener(queueNames = "${aws.queue_comando_realizar_pagamento.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1", acknowledgementMode = ON_SUCCESS)
    public void listenToPaymentCharge(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCreationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var newPayment = new Payment(message.getBody().getOrderId(), PaymentStatus.PENDING);

            paymentUseCase.chargePayment(
                    newPayment,
                    paymentGateway,
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new PaymentCreationException(ExceptionCodes.PAYMENT_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    @SqsListener(queueNames = "${aws.comando_estornar_pagamento.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1", acknowledgementMode = ON_SUCCESS)
    public void listenToPaymentReverse(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCancellationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var newPayment = new Payment(message.getBody().getOrderId(), PaymentStatus.PENDING);

            paymentUseCase.reversePayment(
                    newPayment,
                    paymentGateway,
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new PaymentCancellationException(ExceptionCodes.PAYMENT_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    @SqsListener(queueNames = "${aws.comando_cancelar_solicitacao_pagamento.url}", maxConcurrentMessages = "1", maxMessagesPerPoll = "1", acknowledgementMode = ON_SUCCESS)
    public void listenToPaymentCancellation(MessageHeaders headers, CustomQueueMessage<OrderCommand> message) throws PaymentCancellationException {
        logger.info(
                LoggingPattern.COMMAND_INIT_LOG,
                message.getHeaders().getSagaId(),
                message.getHeaders().getMicrosservice()
        );

        TransactionInformationStorage.fill(message.getHeaders());
        TransactionInformationStorage.putReceiveCount(headers.get(HEADER_RECEIVE_COUNT, String.class));

        try {

            final var newPayment = new Payment(message.getBody().getOrderId(), PaymentStatus.PENDING);

            paymentUseCase.cancelPayment(
                    newPayment,
                    paymentGateway,
                    this);

            logger.info(LoggingPattern.COMMAND_END_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice());

        } catch (Exception ex) {

            logger.info(LoggingPattern.COMMAND_ERROR_LOG,
                    message.getHeaders().getSagaId(),
                    message.getHeaders().getMicrosservice(),
                    ex.getMessage(),
                    message.toString());

            throw new PaymentCancellationException(ExceptionCodes.PAYMENT_07_COMMAND_PROCESSING, ex.getMessage());
        }
    }

    @Override
    public void sendResponse(String orderId, String customerId, String paymentId, OrquestrationStepEnum orquestrationStepEnum, Boolean stepSuccessful) throws PaymentCreationException {

    }
}
