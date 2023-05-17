package org.zaripov.istore.product.conrollers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.zaripov.istore.product.converters.OrderConverter;
import org.zaripov.istore.product.dtos.OrderDto;
import org.zaripov.istore.product.services.OrderService;

import java.util.List;


@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderConverter orderConverter;

    @PostMapping("/{cartId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestHeader String userEmail, @PathVariable String cartId){
        orderService.createNewOrder(userEmail, cartId);

    }

    @GetMapping()
    public List<OrderDto> getOrders(@RequestHeader String userEmail){
        return orderService.findByUserEmail(userEmail)
                .stream()
                .map(orderConverter::toOrderDto)
                .toList();
    }
}
