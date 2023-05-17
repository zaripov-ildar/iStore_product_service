package org.zaripov.istore.product.conrollers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.zaripov.istore.product.dtos.CartDto;
import org.zaripov.istore.product.entities.Order;
import org.zaripov.istore.product.exceptions.ResourceNotFoundException;
import org.zaripov.istore.product.integrations.CartServiceIntegration;
import org.zaripov.istore.product.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderRepository repository;

    @MockBean
    private CartServiceIntegration integration;


    @Test
    void createNewOrder() throws Exception {
        String cartId = "cart_id";
        CartDto cart = new CartDto(new ArrayList<>(), BigDecimal.TEN);
        given(integration.getUserCart(cartId)).willReturn(cart);

        mockMvc.perform(post("/api/v1/order/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userEmail", "qwe@mail.com"))
                .andExpect(status().isCreated());

        verify(repository, times(1)).save(any(Order.class));

    }

    @Test
    void throwBadRequestOnCreatingOrderWithWrongCartId() throws Exception {
        String cartId = "wrong cart_id";
        given(integration.getUserCart(cartId)).willThrow(ResourceNotFoundException.class);

        mockMvc.perform(post("/api/v1/order/" + cartId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("userEmail", "qwe@mail.com"))
                .andExpect(status().isBadRequest());
        verify(repository, never()).save(any());
    }

    @Test
    void getOrders() throws Exception {
        String email = "qwe@mail.com";
        Order order = Order.builder()
                .userEmail(email)
                .totalPrice(BigDecimal.TEN)
                .items(new ArrayList<>())
                .build();
        given(repository.findAllByUserEmail(email)).willReturn(List.of(order));

        mockMvc.perform(get("/api/v1/order")
                .contentType(MediaType.APPLICATION_JSON)
                .header("userEmail", "qwe@mail.com"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].totalPrice", is(10)));
    }

    @Test
    void throws400WhenGetWithEmailHeaderAbsent() throws Exception {
        mockMvc.perform(get("/api/v1/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void throws400WhenPostWithEmailHeaderAbsent() throws Exception {
        mockMvc.perform(post("/api/v1/order/cart_id")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}