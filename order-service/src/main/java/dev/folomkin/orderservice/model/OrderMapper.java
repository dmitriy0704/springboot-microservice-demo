package dev.folomkin.orderservice.model;

import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;

public class OrderMapper {

    public static Order toEntity(String userId, OrderDto dto) {
        return new Order(
                userId,
                dto.getName(),
                dto.getAmount()
        );
    }
}
