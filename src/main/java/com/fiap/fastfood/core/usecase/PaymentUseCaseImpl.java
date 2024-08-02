package com.fiap.fastfood.core.usecase;

import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public class PaymentUseCaseImpl implements PaymentUseCase {

    @Override
    public Payment submit(Payment payment, PaymentGateway paymentGateway) {
        return paymentGateway.save(payment);
    }

    @Override
    public List<Payment> findAll(PaymentGateway paymentGateway) {
        return paymentGateway.findAll();
    }
}
