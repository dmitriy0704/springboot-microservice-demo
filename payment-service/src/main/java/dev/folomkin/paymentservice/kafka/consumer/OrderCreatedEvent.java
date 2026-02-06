package dev.folomkin.paymentservice.kafka.consumer;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String userId,
        String name,
        String status,
        BigDecimal amount
) {
}