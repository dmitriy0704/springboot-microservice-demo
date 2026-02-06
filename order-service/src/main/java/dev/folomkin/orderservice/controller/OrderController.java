package dev.folomkin.orderservice.controller;

import dev.folomkin.orderservice.kafka.producer.OrderCreatedEvent;
import dev.folomkin.orderservice.kafka.producer.OrderEventPublisher;
import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;
import dev.folomkin.orderservice.model.entity.OrderStatus;
import dev.folomkin.orderservice.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderEventPublisher publisher;

    public OrderController(OrderService orderService, OrderEventPublisher publisher) {
        this.orderService = orderService;
        this.publisher = publisher;
    }

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @GetMapping("/hello")
    public String hello() {
        return "Hello from Order Service!";
    }

    @PostMapping("/create-order")
    public ResponseEntity<Order> create(
            @AuthenticationPrincipal Jwt jwt,
            @RequestBody OrderDto orderDto) {
        String userId = jwt.getSubject(); // sub

        log.debug("NEW PAYMENT: {}", jwt.getClaims());

        Order order = orderService.createOrder(userId, orderDto);

        publisher.publish(
                new OrderCreatedEvent(
                        order.getId(),
                        userId,
                        order.getName(),
                        OrderStatus.CREATED,
                        order.getAmount()
                )
        );

        return ResponseEntity.ok().body(order);
    }


    @GetMapping("/all-orders")
    public ResponseEntity<List<Order>> allOrders() {
        return ResponseEntity.ok().body(orderService.allOrders());
    }


    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable("orderId") UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body("Заказ удален");
    }

}
