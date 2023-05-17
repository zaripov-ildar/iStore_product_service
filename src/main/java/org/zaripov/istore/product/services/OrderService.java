package org.zaripov.istore.product.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zaripov.istore.product.converters.CartItemConverter;
import org.zaripov.istore.product.dtos.CartDto;
import org.zaripov.istore.product.entities.Order;
import org.zaripov.istore.product.integrations.CartServiceIntegration;
import org.zaripov.istore.product.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CartItemConverter cartItemConverter;
    private final CartServiceIntegration cartServiceIntegration;


    public void createNewOrder(String userEmail, String cartId) {
        CartDto cartDto = cartServiceIntegration.getUserCart(cartId);
        Order order = Order.builder()
                .totalPrice(cartDto.totalPrice())
                .userEmail(userEmail)
                .build();
        order.setItems(
                cartDto.cartItems()
                        .stream()
                        .map(cartItemDto -> cartItemConverter.toOrderItem(cartItemDto, order))
                        .toList()
        );
        orderRepository.save(order);
        cartServiceIntegration.clear(cartId);
    }

    public List<Order> findByUserEmail(String userEmail) {
        return orderRepository.findAllByUserEmail(userEmail);
    }
}
