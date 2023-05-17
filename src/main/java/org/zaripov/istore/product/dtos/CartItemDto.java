package org.zaripov.istore.product.dtos;

import java.math.BigDecimal;


public record CartItemDto(
        Long productId,
        String productTitle,
        BigDecimal pricePerProduct,
        BigDecimal totalPrice,
        int amount) {
}

