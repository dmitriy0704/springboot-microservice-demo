package dev.folomkin.paymentservice.kafka.producer;


import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentEventPublisher {

    private final KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate;

    public PaymentEventPublisher(KafkaTemplate<String, PaymentCompletedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publish(PaymentCompletedEvent event) {
        kafkaTemplate.send(
                "payment.completed",
                event
        );
    }
}
