package com.jules.persistence;

import com.jules.models.Drink;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DrinksRepository extends MongoRepository<Drink, UUID>, DrinkRepositoryCustom {
}
