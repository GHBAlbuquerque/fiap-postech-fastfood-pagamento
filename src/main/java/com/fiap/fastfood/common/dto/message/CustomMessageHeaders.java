package com.fiap.fastfood.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CustomMessageHeaders {

    private String sagaId;
    private String orderId;
    private String messageType;
    private String microsservice;
}
