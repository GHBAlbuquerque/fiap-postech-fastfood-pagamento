package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.core.entity.Payment;

import java.util.List;

public interface PaymentGateway {

    Payment save(Payment Payment);

    List<Payment> findAll();
}
