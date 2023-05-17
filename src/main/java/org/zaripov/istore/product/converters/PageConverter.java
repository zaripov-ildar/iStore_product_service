package org.zaripov.istore.product.converters;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.zaripov.istore.product.dtos.PageDto;
import org.zaripov.istore.product.dtos.ProductDto;

@Component
public class PageConverter {

    public PageDto toPageDto(Page<ProductDto> page) {
        return PageDto.builder()
                .page(page.getNumber())
                .pageSize(page.getSize())
                .totalPages(page.getTotalPages())
                .items(page.toList())
                .build();
    }
}
