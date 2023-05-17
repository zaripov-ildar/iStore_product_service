package org.zaripov.istore.product.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zaripov.istore.product.dtos.OrderDto;
import org.zaripov.istore.product.entities.Order;


@RequiredArgsConstructor
@Component
public class OrderConverter {

    private final OrderItemConverter orderItemConverter;

    public OrderDto toOrderDto(Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .items(
                        order.getItems()
                                .stream()
                                .map(orderItemConverter::entityToDto)
                                .toList()
                )
                .totalPrice(order.getTotalPrice())
                .build();
    }
}

