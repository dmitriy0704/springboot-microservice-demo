package dev.folomkin.orderservice.model.entity;


import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Автогенерация UUID
    private UUID id;

    private String userid;

    private String name;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Order() {
    }

    public Order(String userid, String name, OrderStatus status, BigDecimal amount) {
        this.userid = userid;
        this.name = name;
        this.status = status;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public void markAsPaid() {
        this.status = OrderStatus.PAID;
    }
}
