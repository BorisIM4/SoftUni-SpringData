package com.example.advquerying.services;

import com.example.advquerying.entities.Label;
import com.example.advquerying.entities.Shampoo;
import com.example.advquerying.entities.Size;
import com.example.advquerying.repositories.ShampooRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShampooServiceImpl implements ShampooService {

    private final ShampooRepository shampooRepository;

    public ShampooServiceImpl(ShampooRepository shampooRepository) {
        this.shampooRepository = shampooRepository;
    }

    @Override
    public List<Shampoo> findAllBySizeOrderById(Size size) {
        return this.shampooRepository.findAllBySizeOrderById(size);
    }

    @Override
    public List<Shampoo> findAllBySizeOrLabelIdOrderByPrice(Size size, Long label_id) {
        return this.shampooRepository.findAllBySizeOrLabelIdOrderByPrice(size, label_id);
    }

    @Override
    public List<Shampoo> findAllByPriceGreaterThanOrderByPriceDesc(BigDecimal price) {
        return this.shampooRepository.findAllByPriceGreaterThanOrderByPriceDesc(price);
    }

    @Override
    public Long countAllByPriceLessThan(BigDecimal price) {
        return this.shampooRepository.countAllByPriceLessThan(price);
    }

    @Override
    public List<String> findAllByIngredientsName(List<String> names) {
        return this.shampooRepository.findAllByIngredientsName(names).stream().map(Shampoo::getBrand).collect(Collectors.toList());
    }

    @Override
    public List<Shampoo> findAllByIngredientsCount(int ingredientCount) {
        return this.shampooRepository.findAllByIngredientsCount(ingredientCount);
    }


}
