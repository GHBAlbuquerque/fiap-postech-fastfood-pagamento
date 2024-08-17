package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public interface PaymentUseCase {

    Payment createPayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway);

    Payment chargePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway);

    Payment reversePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway);

    Payment cancelPayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway);

    List<Payment> findAll(PaymentGateway paymentGateway);
}
