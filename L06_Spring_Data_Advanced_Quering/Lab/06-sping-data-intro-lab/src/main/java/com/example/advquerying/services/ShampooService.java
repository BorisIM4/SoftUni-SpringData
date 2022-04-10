package com.example.advquerying.services;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;

import java.math.BigDecimal;
import java.util.List;

public interface ShampooService {

    List<Shampoo> findAllBySizeOrderById(Size size);

    List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long label_id);

    List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price);

    Long countAllByPriceLessThan(BigDecimal price);

    List<String> findAllByIngredientsName(List<String> names);

    List<Shampoo> findAllByIngredientsCount(int ingredientCount);
}
