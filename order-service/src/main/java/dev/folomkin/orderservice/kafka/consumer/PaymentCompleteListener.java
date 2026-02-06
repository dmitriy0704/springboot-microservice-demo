package dev.folomkin.orderservice.kafka.consumer;


import dev.folomkin.orderservice.model.entity.Order;
import dev.folomkin.orderservice.model.entity.OrderStatus;
import dev.folomkin.orderservice.repository.OrderRepository;
import dev.folomkin.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PaymentCompleteListener {

    private static final Logger log = LoggerFactory.getLogger(PaymentCompleteListener.class);

    private final OrderService service;

    public PaymentCompleteListener(OrderService service) {
        this.service = service;
    }

    @KafkaListener(
            topics = "payment.completed",
            groupId = "order-service"
    )
    @Transactional
    public void on(PaymentCompletedEvent event) {
        log.debug("COMPLETE ORDER IN EVENT {}", event);
        service.completeOrder(event);
    }
}
