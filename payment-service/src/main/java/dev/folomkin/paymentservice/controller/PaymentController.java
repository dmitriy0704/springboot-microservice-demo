package dev.folomkin.paymentservice.controller;


import dev.folomkin.paymentservice.entity.Payment;
import dev.folomkin.paymentservice.entity.UpdateStatusDto;
import dev.folomkin.paymentservice.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final static Logger log = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping("/get-payments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        log.debug("LIST ALL PAYMENTS: {}", service.findAllPayments());
        return ResponseEntity.ok().body(service.findAllPayments());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Payment> updatePaymentStatus(
            @PathVariable UUID id, UpdateStatusDto statusDto) {
        Payment payment = service.updatePaymentStatus(id, statusDto.status());
        return ResponseEntity.ok().body(payment);
    }
}
