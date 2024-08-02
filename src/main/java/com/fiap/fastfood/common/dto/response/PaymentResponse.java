package com.fiap.fastfood.common.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class PaymentResponse {
    private String id;
    private String orderId;
    private String status;
    private LocalDateTime createdAt;
}
