package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.common.logging.LoggingPattern;
import com.fiap.fastfood.common.logging.TransactionInformationStorage;
import com.fiap.fastfood.core.entity.OrquestrationStepEnum;
import com.fiap.fastfood.core.entity.Payment;
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
                    OrquestrationStepEnum.PREPARE_ORDER,
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
                        OrquestrationStepEnum.PREPARE_ORDER,
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
    public Payment chargePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null; //TODO
    }

    @Override
    public Payment reversePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null; //TODO
    }

    @Override
    public Payment cancelPayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null; //TODO
    }

    @Override
    public List<Payment> findAll(PaymentGateway paymentGateway) {
        return paymentGateway.findAll();
    }
}
