package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public class PaymentUseCaseImpl implements PaymentUseCase {


    @Override
    public Payment createPayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public Payment chargePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public Payment reversePayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public Payment cancelPayment(Payment Payment, PaymentGateway paymentGateway, OrquestrationGateway orquestrationGateway) {
        return null;
    }

    @Override
    public List<Payment> findAll(PaymentGateway paymentGateway) {
        return paymentGateway.findAll();
    }
}
