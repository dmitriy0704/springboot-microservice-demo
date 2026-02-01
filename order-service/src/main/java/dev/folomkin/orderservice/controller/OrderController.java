package dev.folomkin.orderservice.controller;

import dev.folomkin.orderservice.model.OrderMapper;
import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @GetMapping("/gethello")
    public String hello() {
        return "Hello from Order Service!";
    }


    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderDto request){
        return OrderMapper.toEntity(request);
    }

}
