package org.zaripov.istore.product.conrollers;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.zaripov.istore.product.converters.PageConverter;
import org.zaripov.istore.product.converters.ProductConverter;
import org.zaripov.istore.product.dtos.PageDto;
import org.zaripov.istore.product.dtos.ProductDto;
import org.zaripov.istore.product.services.ProductService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductConverter productConverter;
    private final PageConverter pageConverter;

    @GetMapping("/find")
    public PageDto getFilteredProductPage(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(name = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(name = "maxPrice", required = false) BigDecimal maxPrice,
            @RequestParam(name = "titlePart", required = false) String titlePart,
            @RequestParam(name = "category", required = false) String category
    ) {
        page = Math.max(1, page);
        pageSize = Math.max(1, pageSize);
        return pageConverter.toPageDto(
                productService.find(page, pageSize, minPrice, maxPrice, titlePart, category)
                        .map(productConverter::toDto));
    }

    @GetMapping("/{id}")
    public ProductDto getProductById(@PathVariable Long id) {
        return productConverter.toDto(productService.findById(id));
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createOrUpdate(@RequestBody ProductDto productDto) {
        productService.createOrUpdate(productDto);
    }

    @DeleteMapping("/{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.deleteById(id);
    }
}
