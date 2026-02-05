package dev.folomkin.orderservice.model.dto;

import dev.folomkin.orderservice.model.entity.Order;

import java.math.BigDecimal;

/**
 * DTO for {@link Order}
 */
public class OrderDto {
    private String name;
    private BigDecimal amount;

    public OrderDto(String name, BigDecimal amount) {
        this.name = name;
        this.amount = amount;
    }

    public OrderDto() {
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
}