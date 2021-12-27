package com.jules.persistence;

import com.jules.models.Ingredient;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, UUID> {
    Ingredient findByName(String name);
}
