package org.zaripov.istore.product.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zaripov.istore.product.entities.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserEmail(String userEmail);
}