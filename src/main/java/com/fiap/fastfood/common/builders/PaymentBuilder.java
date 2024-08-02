package com.fiap.fastfood.common.builders;

import com.fiap.fastfood.common.dto.request.PaymentRequest;
import com.fiap.fastfood.common.dto.response.PaymentResponse;
import com.fiap.fastfood.common.utils.TimeConverter;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.external.orm.PaymentORM;

public class PaymentBuilder {

    public static Payment fromRequestToDomain(PaymentRequest request) {
        return Payment.builder()
                .status("In Progress")
                .orderId(request.getOrderId())
                .build();
    }

    public static PaymentResponse fromDomainToResponse(Payment payment) {
        if (payment == null) return null;

        return PaymentResponse.builder()
                .id(payment.getId())
                .status(payment.getStatus())
                .orderId(payment.getOrderId())
                .createdAt(payment.getCreatedAt())
                .build();
    }

    public static Payment fromOrmToDomain(PaymentORM orm) {
        if (orm == null) return null;

        return Payment.builder()
                .id(orm.getId())
                .status(orm.getStatus())
                .orderId(orm.getOrderId())
                .build();
    }

    public static PaymentORM fromDomainToOrm(Payment payment) {
        var paymentORM = PaymentORM.builder()
                .id(payment.getId())
                .status(payment.getStatus())
                .orderId(payment.getOrderId())
                .build();

        if (payment.getCreatedAt() != null)
            paymentORM.setCreatedAt(TimeConverter.convertToDateViaInstant(payment.getCreatedAt()));

        return paymentORM;
    }
}
