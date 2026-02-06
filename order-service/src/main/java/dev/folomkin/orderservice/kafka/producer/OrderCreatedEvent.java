package dev.folomkin.orderservice.kafka.producer;

import dev.folomkin.orderservice.model.entity.OrderStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderCreatedEvent(
        UUID orderId,
        String userId,

        String name,

        OrderStatus status,
        BigDecimal amount
) {}