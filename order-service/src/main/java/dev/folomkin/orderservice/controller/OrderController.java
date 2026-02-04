package dev.folomkin.orderservice.controller;

import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;
import dev.folomkin.orderservice.service.OrderService;
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

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/hello")
    public String hello() {
        return "Hello from Order Service!";
    }

    @PostMapping("/create-order")
    public ResponseEntity<Order> create(@AuthenticationPrincipal Jwt jwt, @RequestBody OrderDto orderDto) {
        String userId = jwt.getSubject(); // sub
        System.out.println(jwt.getClaims());
        return ResponseEntity.ok().body(orderService.createOrder(userId, orderDto));
    }


    @GetMapping("/all-orders")
    public ResponseEntity<List<Order>> allOrders(){
        return ResponseEntity.ok().body(orderService.allOrders());
    }


//    @PostMapping("/create")
//    @PreAuthorize("isAuthenticated()")
//    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto, Principal principal) {
//        String userId = principal.getName(); // Из JWT
//        UserInfo userInfo = userClient.getUserById(userId); // Feign call to user-service
//        Order order = new Order(orderDto, userId, userInfo.getAddress());
//        orderRepository.save(order);
//        return ResponseEntity.ok(order);
//    }
}
