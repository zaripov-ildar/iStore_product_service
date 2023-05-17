package org.zaripov.istore.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
public class OrderItemDto {
    private Long productId;
    private String productTitle;
    private int amount;
    private BigDecimal pricePerProduct;
    private BigDecimal price;
}
