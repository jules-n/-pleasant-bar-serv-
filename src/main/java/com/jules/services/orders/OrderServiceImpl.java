package com.jules.services.orders;

import com.jules.dtos.DrinkNumberDTO;
import com.jules.dtos.OrderPostDTO;
import com.jules.dtos.OrderStatusDTO;
import com.jules.models.Drink;
import com.jules.models.Order;
import com.jules.models.Status;
import com.jules.persistence.DrinksRepository;
import com.jules.persistence.OrderRepository;
import com.jules.persistence.SequenceRepository;
import com.jules.services.drinks.DrinksService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Setter(onMethod_ = {@Autowired})
    private OrderRepository orderRepository;

    @Setter(onMethod_ = {@Autowired})
    private SequenceRepository sequenceRepository;

    @Setter(onMethod_ = {@Autowired})
    private DrinksRepository drinksRepository;

    private Map<Drink, Integer> findAll(List<DrinkNumberDTO> list) {
        Map<Drink, Integer> result = new HashMap<>();
        list.forEach(
                v -> {
                    result.put(drinksRepository.findDrinkByName(v.getName()).get(), v.getNumber());
                }
        );
        return result;
    }

    @Override
    public Optional<Order> save(OrderPostDTO dto) {
        var drinks = findAll(dto.getDrinks());
        Optional<Order> result = Optional.empty();
        if (isAvailable(drinks)) {
            var order = Order.builder()
                    .creationTime(dto.getCreationTime())
                    .drinks(dto.getDrinks())
                    .number(sequenceRepository.findMaxByCount()+1L)
                    .table(dto.getTable())
                    .check(getCheck(drinks))
                    .status(Status.New)
                    .build();
            sequenceRepository.incrementCount();
            result = Optional.of(orderRepository.save(order));
            //TO DO: implement logic of subtracting totalAmount
        }
        return result;
    }

    private float getCheck(Map<Drink, Integer> drinks) {
        float result = drinks.entrySet().stream()
                .map(entrySet -> entrySet.getKey().getPrice()*entrySet.getValue())
                .reduce(
                0f, Float::sum
        );
        return result;
    }

    private boolean isAvailable(Map<Drink, Integer> drinks) {
       return drinks.entrySet().stream().allMatch(
               entrySet -> {
                   return entrySet.getKey().getTotalAmount() >= entrySet.getKey().getAmount() * entrySet.getValue();
               }
        );
    }

    @Override
    public boolean updateStatus(OrderStatusDTO dto) {
        var existingOrder = orderRepository.findById(dto.getNumber());
        if (existingOrder.isPresent() && existingOrder.get().getStatus().index<dto.getStatus().index) {
            var order = Order.builder().status(dto.getStatus()).number(dto.getNumber()).build();
            return orderRepository.updateStatus(order);
        }
        return false;
    }
}
