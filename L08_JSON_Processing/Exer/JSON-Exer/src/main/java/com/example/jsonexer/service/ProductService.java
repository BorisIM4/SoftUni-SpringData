package com.example.jsonexer.service;

import com.example.jsonexer.model.dto.ProductNameAndPriceDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts() throws IOException;

    List<ProductNameAndPriceDto> findAllProductInRangeOrderByPrice(BigDecimal lower, BigDecimal upper);
}
