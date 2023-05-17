package org.zaripov.istore.product.specifications;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.zaripov.istore.product.entities.Product;

import java.math.BigDecimal;
import java.util.List;
@Component
public class ProductSpecifications {

    public  Specification<Product> categoryLike(List<Long> categories) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.in(
                        root.get("category"))
                        .value(categories));
    }

    public  Specification<Product> priceGraterOrEqualsThan(BigDecimal minPrice) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
    }

    public  Specification<Product> priceLessOrEqualsThan(BigDecimal maxPrice) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("price"), maxPrice));
    }

    public  Specification<Product> titleLike(String titlePart) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("title")),
                        String.format("%%%s%%", titlePart.toLowerCase())
                );
    }
}
