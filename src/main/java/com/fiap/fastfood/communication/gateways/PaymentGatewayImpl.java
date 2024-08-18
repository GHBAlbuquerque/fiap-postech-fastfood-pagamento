package com.fiap.fastfood.communication.gateways;

import com.fiap.fastfood.common.builders.PaymentBuilder;
import com.fiap.fastfood.common.exceptions.custom.EntityNotFoundException;
import com.fiap.fastfood.common.exceptions.custom.ExceptionCodes;
import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.core.entity.Payment;
import com.fiap.fastfood.core.entity.PaymentStatus;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


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
    public Payment updateStatus(String id, PaymentStatus paymentStatus) throws EntityNotFoundException {
        final var optional = paymentRepository.findById(id);

        if (optional.isEmpty()) {
            throw new EntityNotFoundException(
                    ExceptionCodes.PAYMENT_01_NOT_FOUND,
                    String.format("Payment with id %s was not found.", id)
            );
        }

        final var payment = optional.get();

        payment.setStatus(paymentStatus.name());

        return PaymentBuilder.fromOrmToDomain(paymentRepository.save(payment));
    }

    @Override
    public List<Payment> findAll() {
        final var orms = paymentRepository.findAll();

        if (orms.isEmpty()) return new ArrayList<>();

        return orms.stream()
                .map(PaymentBuilder::fromOrmToDomain)
                .toList();
    }
}
