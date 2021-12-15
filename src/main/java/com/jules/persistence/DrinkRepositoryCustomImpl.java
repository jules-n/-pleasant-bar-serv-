package com.jules.persistence;

import com.jules.models.Drink;

import java.util.Optional;

public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom {
    @Override
    public boolean update(String name, Drink drink) {
        return false;
    }

    @Override
    public boolean deleteByName(String name) {
        return false;
    }

    @Override
    public Optional<Drink> findDrinkByName(String name) {
        return Optional.empty();
    }
}
