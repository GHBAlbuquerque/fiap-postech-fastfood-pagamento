package com.fiap.fastfood.common.logging;

public class LoggingPattern {

    public static final String COMMAND_INIT_LOG = "[RESPONSE] SagaId: {} | Command received from {}. ";
    public static final String COMMAND_END_LOG = "[RESPONSE] SagaId: {} | Command successfully received from {}.";
    public static final String COMMAND_ERROR_LOG = "[RESPONSE] SagaId: {} | Error receiving command from {}. | Error Message: {} | Message: {}";

    public static final String RESPONSE_INIT_LOG = "[COMMAND] SagaId: {} | Sending response to {}...";
    public static final String RESPONSE_END_LOG = "[COMMAND] SagaId: {} | Response Succesfully sent to {}.";
    public static final String RESPONSE_ERROR_LOG = "[COMMAND] SagaId: {} | Error sending response to {}. | Error Message: {} | Message: {}";

    public static final String PAYMENT_CREATION_INIT_LOG = "[COMMAND] SagaId: {} | Creating payment.";
    public static final String PAYMENT_CREATION_END_LOG = "[COMMAND] SagaId: {} | Payment created.";
    public static final String PAYMENT_CREATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error creating payment. | Error Message: {} ";

    public static final String PAYMENT_CHARGE_INIT_LOG = "[COMMAND] SagaId: {} | Charging payment...";
    public static final String PAYMENT_CHARGE_CONFIRMATION_LOG = "[COMMAND] SagaId: {} | Payment charged. Updating status to APPROVED.";
    public static final String PAYMENT_CHARGE_END_LOG = "[COMMAND] SagaId: {} | Payment succesfully charged.";
    public static final String PAYMENT_CHARGE_ERROR_LOG = "[COMMAND] SagaId: {} | Error charging payment. | Error Message: {} ";
    public static final String PAYMENT_CHARGE_REJECTION_LOG = "[COMMAND] SagaId: {} | Payment was rejected. Updating status to REJECTED.";

    public static final String PAYMENT_REVERSAL_INIT_LOG = "[COMMAND] SagaId: {} | Reversing payment.";
    public static final String PAYMENT_REVERSAL_END_LOG = "[COMMAND] SagaId: {} | Payment reversed.";
    public static final String PAYMENT_REVERSAL_ERROR_LOG = "[COMMAND] SagaId: {} | Error reversing payment. | Error Message: {} ";

    public static final String PAYMENT_CANCELLATION_INIT_LOG = "[COMMAND] SagaId: {} | Cancelling payment. | Message: {}";
    public static final String PAYMENT_CANCELLATION_END_LOG = "[COMMAND] SagaId: {} | Payment cancelled.";
    public static final String PAYMENT_CANCELLATION_ERROR_LOG = "[COMMAND] SagaId: {} | Error cancelling payment. | Error Message: {} ";
}
