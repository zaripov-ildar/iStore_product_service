package org.zaripov.istore.product.dtos;


import java.math.BigDecimal;
import java.util.List;


public record CartDto(List<CartItemDto> cartItems, BigDecimal totalPrice) {
}
