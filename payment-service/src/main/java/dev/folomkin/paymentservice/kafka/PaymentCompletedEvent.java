package dev.folomkin.paymentservice.kafka;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentCompletedEvent(
        UUID paymentId,
        UUID orderId,
        String userId,
        BigDecimal amount
) {}