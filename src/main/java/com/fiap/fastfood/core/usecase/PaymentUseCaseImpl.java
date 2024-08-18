package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.core.entity.PaymentStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.fiap.fastfood.common.logging.Constants.MAX_RECEIVE_COUNT;

public class PaymentUseCaseImpl implements PaymentUseCase {

    private static final Logger logger = LogManager.getLogger(PaymentUseCaseImpl.class);

    @Override
    public Payment createPayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCreationException {
        logger.info(
                LoggingPattern.PAYMENT_CREATION_INIT_LOG,
                TransactionInformationStorage.getSagaId()
        );

        try {

            final var savedPayment = paymentGateway.save(payment);

            orquestrationGateway.sendResponse(
                    TransactionInformationStorage.getOrderId(),
                    Long.valueOf(TransactionInformationStorage.getCustomerId()),
                    savedPayment.getId(),
                    OrquestrationStepEnum.CREATE_PAYMENT,
                    Boolean.TRUE
            );

            logger.info(
                    LoggingPattern.PAYMENT_CREATION_END_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            return savedPayment;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.PAYMENT_CREATION_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {
                orquestrationGateway.sendResponse(
                        TransactionInformationStorage.getOrderId(),
                        Long.valueOf(TransactionInformationStorage.getCustomerId()),
                        null,
                        OrquestrationStepEnum.CREATE_PAYMENT,
                        Boolean.FALSE
                );
            }

            throw new PaymentCreationException(
                    ExceptionCodes.PAYMENT_02_PAYMENT_CREATION,
                    String.format("Error creating payment. Error: %s", ex.getMessage())
            );
        }
    }

    @Override
    public Payment chargePayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCreationException {
        logger.info(
                LoggingPattern.PAYMENT_CHARGE_INIT_LOG,
                TransactionInformationStorage.getSagaId()
        );

        try {

            Thread.sleep(3000);

            final var savedPayment = paymentGateway.updateStatus(
                    payment.getId(),
                    PaymentStatus.APPROVED
            );

            logger.info(
                    LoggingPattern.PAYMENT_CHARGE_CONFIRMATION_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            orquestrationGateway.sendResponse(
                    TransactionInformationStorage.getOrderId(),
                    Long.valueOf(TransactionInformationStorage.getCustomerId()),
                    savedPayment.getId(),
                    OrquestrationStepEnum.CHARGE_PAYMENT,
                    Boolean.TRUE
            );

            logger.info(
                    LoggingPattern.PAYMENT_CHARGE_END_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            return savedPayment;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.PAYMENT_CHARGE_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {

                logger.info(
                        LoggingPattern.PAYMENT_CHARGE_REJECTION_LOG,
                        TransactionInformationStorage.getSagaId(),
                        ex.getMessage()
                );

                payment.setStatus(PaymentStatus.REJECTED);

                paymentGateway.save(payment);

                orquestrationGateway.sendResponse(
                        TransactionInformationStorage.getOrderId(),
                        Long.valueOf(TransactionInformationStorage.getCustomerId()),
                        payment.getId(),
                        OrquestrationStepEnum.CHARGE_PAYMENT,
                        Boolean.FALSE
                );
            }

            throw new PaymentCreationException(
                    ExceptionCodes.PAYMENT_O9_PAYMENT_CHARGE,
                    String.format("Error charging payment. Error: %s", ex.getMessage())
            );
        }
    }

    @Override
    public Payment reversePayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCancellationException, PaymentCreationException {
        logger.info(
                LoggingPattern.PAYMENT_REVERSAL_INIT_LOG,
                TransactionInformationStorage.getSagaId()
        );

        try {

            Thread.sleep(3000);

            final var savedPayment = paymentGateway.updateStatus(
                    payment.getId(),
                    PaymentStatus.REVERSED
            );

            orquestrationGateway.sendResponse(
                    TransactionInformationStorage.getOrderId(),
                    Long.valueOf(TransactionInformationStorage.getCustomerId()),
                    savedPayment.getId(),
                    OrquestrationStepEnum.REVERSE_PAYMENT,
                    Boolean.TRUE
            );

            logger.info(
                    LoggingPattern.PAYMENT_REVERSAL_END_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            return savedPayment;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.PAYMENT_REVERSAL_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {

                orquestrationGateway.sendResponse(
                        TransactionInformationStorage.getOrderId(),
                        Long.valueOf(TransactionInformationStorage.getCustomerId()),
                        payment.getId(),
                        OrquestrationStepEnum.REVERSE_PAYMENT,
                        Boolean.FALSE
                );
            }

            throw new PaymentCancellationException(
                    ExceptionCodes.PAYMENT_10_PAYMENT_REVERSAL,
                    String.format("Error reversing payment. Error: %s", ex.getMessage())
            );
        }
    }

    @Override
    public Payment cancelPayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCancellationException, PaymentCreationException {
        logger.info(
                LoggingPattern.PAYMENT_CANCELLATION_INIT_LOG,
                TransactionInformationStorage.getSagaId()
        );

        try {

            Thread.sleep(3000);

            final var savedPayment = paymentGateway.updateStatus(
                    payment.getId(),
                    PaymentStatus.CANCELLED
            );

            orquestrationGateway.sendResponse(
                    TransactionInformationStorage.getOrderId(),
                    Long.valueOf(TransactionInformationStorage.getCustomerId()),
                    savedPayment.getId(),
                    OrquestrationStepEnum.CANCEL_PAYMENT,
                    Boolean.TRUE
            );

            logger.info(
                    LoggingPattern.PAYMENT_CANCELLATION_END_LOG,
                    TransactionInformationStorage.getSagaId()
            );

            return savedPayment;

        } catch (Exception ex) {

            logger.error(
                    LoggingPattern.PAYMENT_CANCELLATION_ERROR_LOG,
                    TransactionInformationStorage.getSagaId(),
                    ex.getMessage()
            );

            var receiveCount = Integer.valueOf(TransactionInformationStorage.getReceiveCount());

            if (MAX_RECEIVE_COUNT.equals(receiveCount)) {

                orquestrationGateway.sendResponse(
                        TransactionInformationStorage.getOrderId(),
                        Long.valueOf(TransactionInformationStorage.getCustomerId()),
                        payment.getId(),
                        OrquestrationStepEnum.CANCEL_PAYMENT,
                        Boolean.FALSE
                );
            }

            throw new PaymentCancellationException(
                    ExceptionCodes.PAYMENT_11_PAYMENT_CANCEL,
                    String.format("Error cancelling payment. Error: %s", ex.getMessage())
            );
        }
    }

    @Override
    public List<Payment> findAll(PaymentGateway paymentGateway) {
        return paymentGateway.findAll();
    }
}
