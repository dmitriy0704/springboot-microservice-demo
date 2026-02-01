package dev.folomkin.orderservice.model.dto;

import dev.folomkin.orderservice.model.entity.Order;
import lombok.Value;

/**
 * DTO for {@link Order}
 */
public class OrderDto {
    private String name;
    private int amount;

    public OrderDto(String name, int amount) {
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}