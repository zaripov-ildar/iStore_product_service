package org.zaripov.istore.product.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zaripov.istore.product.dtos.CartItemDto;
import org.zaripov.istore.product.entities.Order;
import org.zaripov.istore.product.entities.OrderItem;
import org.zaripov.istore.product.services.ProductService;

@RequiredArgsConstructor
@Component
public class CartItemConverter {
    private final ProductService productService;

    public OrderItem toOrderItem(CartItemDto cartItemDto, Order order) {
        return OrderItem.builder()
                .order(order)
                .pricePerProduct(cartItemDto.pricePerProduct())
                .amount(cartItemDto.amount())
                .price(cartItemDto.totalPrice())
                .product(
                        productService.findById(cartItemDto.productId())
                )
                .build();
    }
}