package org.zaripov.istore.product.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageDto {
    private List<ProductDto> items;
    private int page;
    private int pageSize;
    private int totalPages;
}
