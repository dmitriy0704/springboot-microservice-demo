package dev.folomkin.orderservice.repository;

import dev.folomkin.orderservice.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
