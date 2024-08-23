package com.fiap.fastfood.common.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class CheckoutRequest {

    private String orderId;
    private Long customerId;
    private String paymentId;
}
