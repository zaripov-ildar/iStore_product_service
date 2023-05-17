package org.zaripov.istore.product.services;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.zaripov.istore.product.converters.ProductConverter;
import org.zaripov.istore.product.dtos.ProductDto;
import org.zaripov.istore.product.entities.Product;
import org.zaripov.istore.product.exceptions.ResourceNotFoundException;
import org.zaripov.istore.product.repositories.CategoryRepository;
import org.zaripov.istore.product.repositories.ProductRepository;
import org.zaripov.istore.product.specifications.ProductSpecifications;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final CategoryRepository categoryRepository;
    private final ProductSpecifications specifications;

    public Page<Product> find(Integer page, Integer pageSize, BigDecimal minPrice, BigDecimal maxPrice, String titlePart, String category) {
        Specification<Product> spec = Specification.where(null);

        if (category!=null){
            List<Long> categories = categoryRepository.findIdByTitleContainingIgnoreCase(category);
            spec = spec.and(specifications.categoryLike(categories));
        }
        if (minPrice != null) {
            spec = spec.and(specifications.priceGraterOrEqualsThan(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(specifications.priceLessOrEqualsThan(maxPrice));
        }
        if (titlePart != null) {
            String decodedTitle = decode(titlePart);
            spec = spec.and(specifications.titleLike(decodedTitle));
        }
        return productRepository.findAll(spec, PageRequest.of(page - 1, pageSize));
    }

    public void createOrUpdate(ProductDto productDto) {
        productRepository.save(productConverter.toProduct(productDto));
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Can't find product by id: " + id));
    }

    private String decode(String value)  {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }
}
