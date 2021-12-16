package com.jules.controllers;

import com.jules.dtos.DrinkAmountDTO;
import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.Menu;
import com.jules.services.drinks.DrinksService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drinks")
public class DrinksControllers {

    @Setter(onMethod_ = @Autowired)
    private DrinksService service;

    @GetMapping("menu")
    public ResponseEntity<Menu> getMenu() {
        var menu = service.getMenu();
        return ResponseEntity.ok(menu);
    }

    @PutMapping("renew-drink")
    public ResponseEntity renewDrink(@RequestBody DrinkAmountDTO dto) {
        var result = service.updateTotalAmountOfDrink(dto);
        return result?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @PutMapping("update-drink")
    public ResponseEntity updateDrink(@RequestBody DrinkFullInfoDTO dto, @RequestParam String drinkName) {
        var result = service.updateDrink(drinkName, dto);
        return result?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @PostMapping("add-drink")
    public ResponseEntity addDrink(@RequestBody DrinkFullInfoDTO dto) {
        var result = service.save(dto);
        return result.isPresent()?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete-drink")
    public ResponseEntity dropDrink(String name) {
        var result = service.deleteDrink(name);
        return result?ResponseEntity.ok().build():ResponseEntity.badRequest().build();
    }
}
