package org.zaripov.istore.product.integrations;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.zaripov.istore.product.dtos.CartDto;
import org.zaripov.istore.product.exceptions.ResourceNotFoundException;

@Component
@RequiredArgsConstructor
public class CartServiceIntegration {
    private final WebClient cartServiceWebClient;

    public CartDto getUserCart(String cartId) {
        return cartServiceWebClient
                .get()
                .uri("/api/v1/cart/" + cartId)
                .retrieve()
                .onStatus(
                        HttpStatusCode::is4xxClientError,
                        clientResponse -> clientResponse.bodyToMono(ResourceNotFoundException.class)
                                .map(body -> new ResourceNotFoundException("Something wrong on cart service: " + clientResponse.statusCode()))
                )
                .bodyToMono(CartDto.class)
                .block();
    }

    public void clear(String cartId) {
        cartServiceWebClient
                .get()
                .uri("/api/v1/cart/clear/" + cartId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }
}

