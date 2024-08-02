package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.PaymentBuilder;
import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Repository
public class PaymentGatewayImpl implements PaymentGateway {

    private final PaymentRepository paymentRepository;

    public PaymentGatewayImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment save(Payment payment) {
        final var orm = PaymentBuilder.fromDomainToOrm(payment);
        final var result = paymentRepository.save(orm);

        return PaymentBuilder.fromOrmToDomain(result);
    }

    @Override
    public List<Payment> findAll() {
        final var orms = paymentRepository.findAll();

        if (orms.isEmpty()) return new ArrayList<>();

        final var payments = orms.stream()
                .map(PaymentBuilder::fromOrmToDomain)
                .collect(Collectors.toList());

        return payments;
    }
}
