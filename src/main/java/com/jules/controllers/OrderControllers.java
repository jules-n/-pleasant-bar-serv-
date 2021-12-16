package com.jules.controllers;

import com.jules.dtos.OrderPostDTO;
import com.jules.dtos.OrderStatusDTO;
import com.jules.kafka.Producer;
import com.jules.services.drinks.DrinksService;
import com.jules.services.orders.OrderService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("orders")
public class OrderControllers {

    @Setter(onMethod_ = @Autowired)
    private OrderService orderService;

    @Setter(onMethod_ = @Autowired)
    private DrinksService drinksService;

    @Setter(onMethod_ = @Autowired)
    private Producer producer;

    @Value("${cloudkarafka.menu-topic}")
    private String menuTopic;

    @Value("${cloudkarafka.status-topic}")
    private String statusTopic;

    @PutMapping("update-status")
    public ResponseEntity updateStatus(@RequestBody OrderStatusDTO dto) {
        var result = orderService.updateStatus(dto);
        if (result) {
            producer.send(dto, statusTopic);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("create-order")
    public ResponseEntity<Double> createOrder(@RequestBody OrderPostDTO dto) {
        var result = orderService.save(dto);
        if (result.isPresent()) {
            producer.send(drinksService.getMenu(), menuTopic);
            return ResponseEntity.ok(result.get().getCheck());
        }
        return ResponseEntity.badRequest().build();
    }
}
