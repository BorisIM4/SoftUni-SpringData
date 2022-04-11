package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    public List<Ingredient> findAllByNameIsStartingWith(String name) {
        return this.ingredientRepository.findAllByNameIsStartingWith(name);
    }

    @Override
    public List<Ingredient> findAllByNameIn(Collection<String> name) {
        return this.ingredientRepository.findAllByNameIn(name);
    }

    @Override
    @Transactional
    public int deleteAllByName(String name) {
        return this.ingredientRepository.deleteAllByName(name);
    }

    @Override
    @Transactional
    public int increaseAllPricesWith10Percents() {
        return this.ingredientRepository.increaseAllPricesWith10Percents();
    }

    @Override
    public List<Ingredient> findAll() {
        return this.ingredientRepository.findAll();
    }

    @Override
    @Transactional
    public int updatesPriceByName(BigDecimal priceChange, Collection<String> names) {
        return this.ingredientRepository.updatesPriceByName(priceChange, names);
    }


}
