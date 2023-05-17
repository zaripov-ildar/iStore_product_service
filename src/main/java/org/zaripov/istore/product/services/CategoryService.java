package org.zaripov.istore.product.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.zaripov.istore.product.entities.Category;
import org.zaripov.istore.product.repositories.CategoryRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<Category> findByTitle(String categoryTitle) {
        return categoryRepository.findByTitle(categoryTitle);
    }
}
