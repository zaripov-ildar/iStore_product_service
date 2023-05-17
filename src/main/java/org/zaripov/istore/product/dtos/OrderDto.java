package org.zaripov.istore.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class OrderDto {
    private Long id;
    private List<OrderItemDto> items;
    private BigDecimal totalPrice;
}