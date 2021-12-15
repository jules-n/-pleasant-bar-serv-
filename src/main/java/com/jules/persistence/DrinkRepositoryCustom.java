package com.jules.persistence;

import com.jules.models.Drink;

import java.util.Optional;

public interface DrinkRepositoryCustom {
    boolean update(String name, Drink drink);
    boolean deleteByName(String name);
    Optional<Drink> findDrinkByName(String name);
}
