package org.zaripov.istore.product.converters;

import org.junit.jupiter.api.Test;
import org.zaripov.istore.product.dtos.OrderItemDto;
import org.zaripov.istore.product.entities.OrderItem;
import org.zaripov.istore.product.entities.Product;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemConverterTest {

    @Test
    void entityToDto() {
        OrderItemConverter converter = new OrderItemConverter();
        OrderItem item = OrderItem.builder()
                .product(Product.builder()
                        .title("product")
                        .build())
                .price(BigDecimal.TEN)
                .amount(10)
                .pricePerProduct(BigDecimal.ONE)
                .build();
        OrderItemDto dto = converter.entityToDto(item);
        assertEquals(dto.getAmount(), item.getAmount());
        assertEquals(dto.getProductTitle(), item.getProduct().getTitle());
        assertEquals(dto.getPricePerProduct(), item.getPricePerProduct());
        assertEquals(dto.getPrice(), item.getPrice());

    }
}