package com.fiap.fastfood.common.interfaces.gateways;

import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.core.entity.PaymentStatus;

import java.util.List;

public interface PaymentGateway {

    Payment save(Payment Payment);

    Payment updateStatus(String id, PaymentStatus paymentStatus) throws EntityNotFoundException;

    List<Payment> findAll();
}
