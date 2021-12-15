package com.jules.controllers;

import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.Menu;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drinks")
public class DrinksControllers {

    @GetMapping("menu")
    public ResponseEntity<Menu> getMenu() {
        return ResponseEntity.ok().build();
    }

    @PutMapping("renew-drinks")
    public ResponseEntity renewDrinks(@RequestBody DrinkFullInfoDTO dto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("add-drink")
    public ResponseEntity addDrink(@RequestBody DrinkFullInfoDTO dto) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("delete-drink")
    public ResponseEntity dropDrink(String name) {
        return ResponseEntity.ok().build();
    }
}
