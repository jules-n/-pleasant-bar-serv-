package com.jules.controllers;

import com.jules.dtos.OrderPostDTO;
import com.jules.dtos.OrderStatusDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderControllers {

    @PutMapping("update-status")
    public ResponseEntity renewDrinks(@RequestBody OrderStatusDTO dto) {
        return ResponseEntity.ok().build();
    }

    @PostMapping("create-order")
    public ResponseEntity addDrink(@RequestBody OrderPostDTO dto) {
        return ResponseEntity.ok().build();
    }
}
