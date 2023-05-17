package org.zaripov.istore.product.entities;


import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "price_per_product")
    private BigDecimal pricePerProduct;

    @Column(name = "amount")
    private int amount;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
