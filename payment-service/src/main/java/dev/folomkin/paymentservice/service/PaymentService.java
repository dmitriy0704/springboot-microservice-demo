package dev.folomkin.paymentservice.service;


import dev.folomkin.paymentservice.entity.Payment;
import dev.folomkin.paymentservice.entity.PaymentStatus;
import dev.folomkin.paymentservice.kafka.consumer.OrderCreatedEvent;
import dev.folomkin.paymentservice.kafka.producer.PaymentCompletedEvent;
import dev.folomkin.paymentservice.kafka.producer.PaymentEventPublisher;
import dev.folomkin.paymentservice.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Slf4j
@Service
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentEventPublisher eventPublisher;


    public PaymentService(PaymentRepository repository, PaymentEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void createPayment(OrderCreatedEvent event) {

        if (repository.existsByOrderId(event.orderId())) {
            log.info("Payment already exists for order {}", event.orderId());
            return;
        }

        Payment payment = new Payment();
        payment.setOrderId(event.orderId());
        payment.setUserId(event.userId());
        payment.setName(event.name());
        payment.setStatus(PaymentStatus.NEW);
        payment.setAmount(event.amount());

        log.debug("NEW PAYMENT: {}", payment);

        repository.save(payment);
    }


    public List<Payment> findAllPayments() {
        return repository.findAll();
    }


    @Transactional
    public void completePayment(UUID paymentId) {

        Payment payment = repository.findById(paymentId)
                .orElseThrow(()-> new EntityNotFoundException("Платеж не найден"));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            return; // идемпотентность
        }

        payment.setStatus(PaymentStatus.PAID);

        repository.save(payment);

        eventPublisher.publish(
                new PaymentCompletedEvent(
                        payment.getId(),
                        payment.getOrderId(),
                        payment.getUserId(),
                        payment.getName(),
                        payment.getAmount()
                )
        );
    }


    public void deletePayment(UUID paymentId){
        Payment payment = repository.findById(paymentId).orElseThrow(
                ()-> new EntityNotFoundException("Такой платеж не найден")
        );
        repository.delete(payment);
    }
}
