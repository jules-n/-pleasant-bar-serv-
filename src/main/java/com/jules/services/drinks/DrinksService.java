package com.jules.services.drinks;

import com.jules.dtos.DrinkAmountDTO;
import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.Menu;
import com.jules.models.Drink;

import java.util.Optional;

public interface DrinksService {
    boolean deleteDrink(String name);
    Optional<Drink> save(DrinkFullInfoDTO dto);
    boolean updateDrink(String name, DrinkFullInfoDTO dto);
    boolean updateTotalAmountOfDrink(DrinkAmountDTO dto);
    Menu getMenu();
}
