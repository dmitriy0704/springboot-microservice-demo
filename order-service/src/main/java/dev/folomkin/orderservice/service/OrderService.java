package dev.folomkin.orderservice.service;

import dev.folomkin.orderservice.model.OrderMapper;
import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;
import dev.folomkin.orderservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(String userId,  OrderDto orderDto){
        return orderRepository.save(OrderMapper.toEntity(userId, orderDto));
    }

    public List<Order> allOrders(){
        return orderRepository.findAll();
    }
}
