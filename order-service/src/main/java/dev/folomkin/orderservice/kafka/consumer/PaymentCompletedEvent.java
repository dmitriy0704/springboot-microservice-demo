package dev.folomkin.orderservice.kafka.consumer;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentId,
        UUID orderId,
        String userId,
        String name,
        BigDecimal amount
) {}