package dev.folomkin.paymentservice.service;


import dev.folomkin.paymentservice.entity.Payment;
import dev.folomkin.paymentservice.entity.PaymentStatus;
import dev.folomkin.paymentservice.kafka.OrderCreatedEvent;
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

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
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
        payment.setAmount(event.amount());
        payment.setStatus(PaymentStatus.NEW);

        log.debug("NEW PAYMENT: {}", payment);

        repository.save(payment);
    }


    public List<Payment> findAllPayments() {
        return repository.findAll();
    }


    public Payment updatePaymentStatus(UUID paymentId, PaymentStatus newStatus) {

        Payment payment = repository.findById(paymentId).orElseThrow(
                () -> new EntityNotFoundException("Платеж с ID " + paymentId + " не найден")
        );

        payment.setStatus(newStatus);
        repository.save(payment);
        return payment;
    }

}
