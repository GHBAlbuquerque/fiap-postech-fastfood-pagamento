package com.fiap.fastfood.common.logging;

public class Constants {
    public static final String MESSAGE_TYPE_COMMAND = "COMMAND";
    public static final String MESSAGE_TYPE_RESPONSE = "RESPONSE";

    public static final String MS_ORDER = "ms_pedido";
    public static final String MS_CUSTOMER = "ms_cliente";
    public static final String MS_PAYMENT = "ms_pagamento";
    public static final String MS_PRODUCT = "ms_producto";
    public static final String MS_SAGA = "ms_orquestrador";

    public static final Integer MAX_RECEIVE_COUNT = 5;
    public static final String HEADER_RECEIVE_COUNT = "Sqs_Msa_ApproximateReceiveCount";
}
