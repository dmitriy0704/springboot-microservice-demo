package dev.folomkin.paymentservice.kafka.producer;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentId,
        UUID orderId,
        String userId,
        String name,
        BigDecimal amount
) {}