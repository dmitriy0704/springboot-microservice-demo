package dev.folomkin.orderservice.service;

import dev.folomkin.orderservice.kafka.consumer.PaymentCompletedEvent;
import dev.folomkin.orderservice.model.OrderMapper;
import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;
import dev.folomkin.orderservice.model.entity.OrderStatus;
import dev.folomkin.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public Order createOrder(String userId,  OrderDto orderDto){
        return repository.save(OrderMapper.toEntity(userId, orderDto));
    }

    public List<Order> allOrders(){
        return repository.findAll();
    }


    @Transactional
    public void completeOrder(PaymentCompletedEvent event) {
        Order order = repository.findById(event.orderId())
                .orElseThrow();
        if (order.getStatus() == OrderStatus.PAID) {
            return; // идемпотентность
        }
        order.markAsPaid();
        repository.save(order);
    }


    public void deleteOrder(UUID orderId){
        Order order = repository.findById(orderId).orElseThrow(
                ()-> new EntityNotFoundException("Такой заказ не найден")
        );
        repository.delete(order);
    }
}
