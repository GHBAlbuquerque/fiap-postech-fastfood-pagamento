package com.fiap.fastfood.common.beans;

import com.fiap.fastfood.common.interfaces.datasources.PaymentRepository;
import com.fiap.fastfood.common.interfaces.external.MessageSender;
import com.fiap.fastfood.common.interfaces.gateways.OrquestrationGateway;
import com.fiap.fastfood.common.interfaces.gateways.PaymentGateway;
import com.fiap.fastfood.common.interfaces.usecase.PaymentUseCase;
import com.fiap.fastfood.communication.gateways.OrquestrationGatewayImpl;
import com.fiap.fastfood.communication.gateways.PaymentGatewayImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayBeanDeclaration {

    @Bean
    public PaymentGateway paymentGateway(PaymentRepository repository) {
        return new PaymentGatewayImpl(repository);
    }

    @Bean
    public OrquestrationGateway orquestrationGateway(PaymentUseCase paymentUseCase, PaymentGateway paymentGateway, MessageSender messageSender) {
        return new OrquestrationGatewayImpl(paymentUseCase, paymentGateway, messageSender);
    }
}
