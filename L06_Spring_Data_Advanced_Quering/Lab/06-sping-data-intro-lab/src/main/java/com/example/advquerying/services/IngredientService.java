package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public interface IngredientService {

    List<Ingredient> findAllByNameIsStartingWith(String name);

    List<Ingredient> findAllByNameIn(Collection<String> name);

    int deleteAllByName(String name);

    int increaseAllPricesWith10Percents();

    List<Ingredient> findAll();

    int updatesPriceByName(BigDecimal priceChange, Collection<String> names);

}
