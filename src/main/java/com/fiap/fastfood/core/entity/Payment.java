package com.fiap.fastfood.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private String id;
    private String orderId;
    private PaymentStatus status;
    private LocalDateTime createdAt;

    public Payment(String orderId, PaymentStatus status) {
        this.orderId = orderId;
        this.status = status;
    }
}
