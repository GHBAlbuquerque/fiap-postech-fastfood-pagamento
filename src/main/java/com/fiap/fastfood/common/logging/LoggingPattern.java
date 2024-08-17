package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[RESPONSE] SagaId: {} | Command received from {}. ";
    public static final String COMMAND_END_LOG = "[RESPONSE] SagaId: {} | Command successfully received from {}.";
    public static final String COMMAND_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving command from {}. | Error Message: {} | Message: {}";

    public static final String RESPONSE_INIT_LOG = "[COMMAND] SagaId: {} | Sending Response to {}...";
    public static final String RESPONSE_END_LOG = "[COMMAND] SagaId: {} | Response Succesfully sent to {}.";
    public static final String RESPONSE_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending response to {}. | Error Message: {} | Message: {}";

    public static final String ORDER_CREATION_INIT_LOG = "[COMMAND] SagaId: {} | Creating order.";
    public static final String ORDER_CREATION_END_LOG = "[COMMAND] SagaId: {} | Order created.";
    public static final String ORDER_CREATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error creating order. | Error Message: {} ";

    public static final String ORDER_PREPARATION_INIT_LOG = "[COMMAND] SagaId: {} | Preparing order.";
    public static final String ORDER_PREPARATION_END_LOG = "[COMMAND] SagaId: {} | Order prepared.";
    public static final String ORDER_PREPARATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error preparing order. | Error Message: {} ";

    public static final String ORDER_COMPLETION_INIT_LOG = "[COMMAND] SagaId: {} | Completing order.";
    public static final String ORDER_COMPLETION_END_LOG = "[COMMAND] SagaId: {} | Order completed.";
    public static final String ORDER_COMPLETION_ERROR_LOG = "[COMMAND] SagaId: {} | Error completing order. | Error Message: {} ";

    public static final String ORDER_CANCELLATION_INIT_LOG = "[COMMAND] SagaId: {} | Cancelling order. | Message: {}";
    public static final String ORDER_CANCELLATION_END_LOG = "[COMMAND] SagaId: {} | Order cancelled.";
    public static final String ORDER_CANCELLATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error cancelling order. | Error Message: {} ";
}
