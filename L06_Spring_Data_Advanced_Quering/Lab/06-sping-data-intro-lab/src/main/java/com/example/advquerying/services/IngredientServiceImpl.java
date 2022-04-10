package com.example.advquerying.services;

import com.example.advquerying.entities.Ingredient;
import com.example.advquerying.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

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
}
