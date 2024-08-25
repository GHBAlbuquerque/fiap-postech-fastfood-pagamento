package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.exceptions.custom.PaymentCancellationException;
import com.fiap.fastfood.common.exceptions.custom.PaymentCreationException;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public interface PaymentUseCase {

    Payment createPayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCreationException;

    Payment chargePayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCreationException;

    Payment reversePayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCancellationException, PaymentCreationException;

    Payment cancelPayment(Payment payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) throws PaymentCancellationException, PaymentCreationException;

    List<Payment> findAll(PaymentGateway paymentGateway);
}
