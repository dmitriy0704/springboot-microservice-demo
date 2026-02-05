package dev.folomkin.orderservice.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String userId,

        String name,
        BigDecimal amount
) {}