package org.zaripov.istore.product.converters;

import org.springframework.stereotype.Component;
import org.zaripov.istore.product.dtos.OrderItemDto;
import org.zaripov.istore.product.entities.OrderItem;

@Component
public class OrderItemConverter {
    public OrderItemDto entityToDto(OrderItem item) {
        return OrderItemDto.builder()
                .productId(item.getId())
                .productTitle(item.getProduct().getTitle())
                .amount(item.getAmount())
                .pricePerProduct(item.getPricePerProduct())
                .price(item.getPrice())
                .build();
    }
}
