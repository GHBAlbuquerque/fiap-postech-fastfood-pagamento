package com.fiap.fastfood.common.interfaces.usecase;

import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public interface PaymentUseCase {

    Payment submit(Payment Payment, PaymentGateway paymentGateway);

    List<Payment> findAll(PaymentGateway paymentGateway);
}
