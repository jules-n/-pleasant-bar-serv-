package com.jules.services.ingredients;

import com.jules.dtos.IngredientPostDTO;
import com.jules.models.Ingredient;

import java.util.Optional;

public interface IngredientService {
    void save(IngredientPostDTO dto);

    Optional<Ingredient> findByName(String name);

    void update(String name, IngredientPostDTO dto);

    void delete(String name);
}
