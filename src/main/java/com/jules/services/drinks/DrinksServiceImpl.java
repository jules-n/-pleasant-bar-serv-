package com.jules.services.drinks;

import com.jules.dtos.DrinkAmountDTO;
import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.DrinkGetDTO;
import com.jules.dtos.Menu;
import com.jules.models.Component;
import com.jules.models.Drink;
import com.jules.persistence.DrinksRepository;
import lombok.Setter;
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

    @Override
    public boolean deleteDrink(String name) {
        var allDrinks = repository.findAll();
        var count = allDrinks.stream().filter(drink -> !drink.getName().equals(name))
                .filter(drink -> !(drink.getComponents() != null && drink.getComponents().isEmpty()))
                .map(
                        drink -> findComponent(drink.getComponents(), name)
                )
                .filter(Objects::nonNull)
                .count();
        if (count != 0) throw new RuntimeException("It's a component for another brew");
        return repository.deleteByName(name);
    }

    private List<String> findComponent(Set<Component> set, String nameOfDrinkToDelete) {
        List<String> list = set.stream().map(
                component -> {
                    String name = null;
                    var drink = repository.findDrinkByName(nameOfDrinkToDelete);
                    if(drink.isPresent()) {
                        if(Objects.requireNonNull(drink.get().getComponents()).isEmpty()
                                && drink.get().getName().equals(nameOfDrinkToDelete)) {
                            name = drink.get().getName();
                        }
                       if(Objects.requireNonNull(drink.get().getComponents()).isEmpty()){
                           List<String> includedList =
                                   findComponent(Objects.requireNonNull(drink.get().getComponents()), nameOfDrinkToDelete);
                           name = includedList.stream().findFirst().get();
                       }
                    }
                    return name;
                }
        ).collect(Collectors.toList());
        return list;
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
                    if (drink.getComponents() != null) {
                        if(drink.getComponents().isEmpty() && drink.getAmount()<drink.getTotalAmount()) {
                           // positions.add();
                        }
                    }
                }
        );
        menu.setPositions(positions);
        return null;
    }
}
