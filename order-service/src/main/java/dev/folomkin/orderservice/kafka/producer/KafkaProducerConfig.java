package dev.folomkin.orderservice.kafka.producer;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@Configuration
public class KafkaProducerConfig {

    @Bean
    public KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate(
            ProducerFactory<String, OrderCreatedEvent> factory) {
        return new KafkaTemplate<>(factory);
    }
}
