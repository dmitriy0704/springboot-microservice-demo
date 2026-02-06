package dev.folomkin.paymentservice.kafka.consumer;

import dev.folomkin.paymentservice.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreatedListener {

    private final PaymentService paymentService;

    public OrderCreatedListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(
            topics = "order.created",
            groupId = "payment-service"
    )
    public void on(OrderCreatedEvent event) {
        log.debug("RECEIVED EVENT: {}", event);
        paymentService.createPayment(event);
    }
}