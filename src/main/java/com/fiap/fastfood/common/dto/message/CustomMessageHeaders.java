package com.fiap.fastfood.common.dto.message;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomMessageHeaders {

    private String sagaId;
    private String orderId;
    private String messageType;
    private String microsservice;
}
