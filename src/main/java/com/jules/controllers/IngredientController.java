package com.jules.controllers;

import com.jules.dtos.DrinkFullInfoDTO;
import com.jules.dtos.IngredientPostDTO;
import com.jules.services.ingredients.IngredientService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ingredients")
public class IngredientController {

    @Setter(onMethod_ = {@Autowired})
    private IngredientService service;

    @PutMapping("update-ingredient")
    public ResponseEntity updateIngredient(@RequestBody IngredientPostDTO dto, @RequestParam String name) {
        service.update(name, dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("add-ingredient")
    public ResponseEntity addIngredient(@RequestBody IngredientPostDTO dto) {
        service.save(dto);
        return  ResponseEntity.ok().build();
    }
}
