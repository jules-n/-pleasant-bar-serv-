package com.jules.services.drinks;

import com.jules.dtos.DrinkAmountDTO;
import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.DrinkGetDTO;
import com.jules.dtos.Menu;
import com.jules.models.Drink;
import com.jules.persistence.DrinksRepository;
import com.jules.services.ingredients.IngredientService;
import lombok.Setter;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DrinksServiceImpl implements DrinksService {

    @Setter(onMethod_ = {@Autowired})
    private DrinksRepository repository;

    @Setter(onMethod_ = {@Autowired})
    private ModelMapper modelMapper;

    @Setter(onMethod_ = {@Autowired})
    private IngredientService service;

    @Override
    public boolean deleteDrink(String name) {
        var allDrinks = repository.findAll();
        var count = allDrinks.stream().filter(drink -> !drink.getName().equals(name))
                .filter(drink -> !(drink.getComponents() != null && drink.getComponents().isEmpty()))
                .map(
                        drink -> {
                            var allComponents = findSubDrinks(drink);
                            var allComponentsName = allComponents.stream().map(Drink::getName);
                            return allComponentsName.anyMatch(name::equals);
                        }
                )
                .filter(result -> result)
                .count();
        if (count > 0) throw new RuntimeException("It's a component for another brew");
        return repository.deleteByName(name);
    }

    @SneakyThrows
    public List<Drink> findAllBasicDrinks(String drinkName) {
        var drink = repository.findDrinkByName(drinkName);
        if (drink.isEmpty()) throw new Exception("No such drink");
        return findSubDrinks(drink.get());
    }

    @SneakyThrows
    /**
     * Search was simplified in cause of all cocktails has only one level of nesting in components.
     * If you know any cocktail/shot that contain another cocktails/shots write author (bonkers.superhero@gmail.com)
     *
     * Thanks,
     * Jules
     */
    private List<Drink> findSubDrinks(Drink drink) {
        if (drink.getComponents() == null || drink.getComponents().isEmpty()) return List.of();
        List<Drink> subDrinks = drink.getComponents().stream().map(
                component -> {
                    var subDrink = repository.findDrinkByName(component.getName()).get();
                    return subDrink;
                }
        ).collect(Collectors.toList());
        return subDrinks;
    }

    @Override
    public Optional<Drink> save(DrinkFullInfoDTO dto) {
        var drink = modelMapper.map(dto, Drink.class);
        drink.setId(UUID.randomUUID());
        repository.save(drink);
        return Optional.of(drink);
    }

    @Override
    public boolean updateDrink(String name, DrinkFullInfoDTO dto) {
        var drink = modelMapper.map(dto, Drink.class);
        var existingDrink = repository.findDrinkByName(name);
        existingDrink.ifPresent(value -> drink.setId(value.getId()));
        var result = repository.update(name, drink);
        return result;
    }

    @Override
    public boolean updateTotalAmountOfDrink(DrinkAmountDTO dto) {
        var drink = repository.findDrinkByName(dto.getName());
        drink.ifPresent(value -> drink.get().setTotalAmount(dto.getTotalAmount()));
        var result = repository.update(dto.getName(), drink.get());
        return result;
    }

    @Override
    public Menu getMenu() {
        var menu = new Menu();
        var positions = new ArrayList<DrinkGetDTO>();
        var allDrinks = repository.findAll();
        allDrinks.forEach(
                drink -> {
                    var subDrinks = findSubDrinks(drink);
                    if (subDrinks.isEmpty()) {
                       if (checkAmount(drink, drink.getAmount()))
                        positions.add(modelMapper.map(drink, DrinkGetDTO.class));
                    } else {
                        var allPresent = subDrinks.stream().map(
                                subDrink -> {
                                    var amountOfSubDrink = drink.getComponents().stream().filter(
                                            component -> component.getName().equals(subDrink.getName())
                                    ).findFirst().get();
                                    return checkAmount(subDrink, amountOfSubDrink.getAmount());
                                }
                        ).filter(result -> !result).findAny().get();
                        if (allPresent) positions.add(modelMapper.map(drink, DrinkGetDTO.class));
                    }
                }
        );
        menu.setPositions(positions);
        return menu;
    }

    public boolean checkAmount(Drink drink, float amount) {
        var isEnoughDrinksAmount = amount <= drink.getTotalAmount();
        var isEnoughIngredients = !drink.getNonDrinksComponent().stream()
                .map(
                        component -> {
                            var ingredient = service.findByName(component.getName());
                            return ingredient.isPresent() && ingredient.get().getTotalAmount() >= component.getAmount();
                        }
                )
                .filter(result -> !result)
                .findAny()
                .get();
        return isEnoughDrinksAmount && isEnoughIngredients;
    }
}
