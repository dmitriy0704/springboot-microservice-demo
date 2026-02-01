package dev.folomkin.orderservice.model;

import dev.folomkin.orderservice.model.dto.OrderDto;
import dev.folomkin.orderservice.model.entity.Order;

public class OrderMapper {

    public static Order toEntity(OrderDto dto) {
        return new Order(
                1L,
                dto.getName(),
                dto.getAmount()
        );
    }
}
