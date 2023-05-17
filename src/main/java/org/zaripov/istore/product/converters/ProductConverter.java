package org.zaripov.istore.product.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.zaripov.istore.product.dtos.ProductDto;
import org.zaripov.istore.product.entities.Category;
import org.zaripov.istore.product.entities.Product;
import org.zaripov.istore.product.exceptions.ResourceNotFoundException;
import org.zaripov.istore.product.services.CategoryService;

@Component
@RequiredArgsConstructor
public class ProductConverter {

    private final CategoryService categoryService;

    public ProductDto toDto(Product product) {
        return ProductDto.builder()
                .id(product.getId())
                .title(product.getTitle())
                .categoryTitle(product.getCategory().getTitle())
                .price(product.getPrice())
                .description(product.getDescription())
                .build();
    }

    public Product toProduct(ProductDto productDto) {
        Category category = categoryService.findByTitle(productDto.getCategoryTitle())
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                String.format("There is no such category '%s'! before creating a product, create a category",
                                        productDto.getCategoryTitle())));

        return Product.builder()
                .title(productDto.getTitle())
                .price(productDto.getPrice())
                .category(category)
                .description(productDto.getDescription())
                .build();
    }
}
