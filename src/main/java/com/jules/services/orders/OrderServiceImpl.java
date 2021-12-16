package com.jules.services.orders;

import com.jules.dtos.OrderPostDTO;
import com.jules.dtos.OrderStatusDTO;
import com.jules.models.Drink;
import com.jules.models.Order;
import com.jules.models.Status;
import com.jules.persistence.OrderRepository;
import com.jules.persistence.SequenceRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Setter(onMethod_ = {@Autowired})
    private OrderRepository orderRepository;

    @Setter(onMethod_ = {@Autowired})
    private SequenceRepository sequenceRepository;

    @Override
    public Optional<Order> save(OrderPostDTO dto) {
        Optional<Order> result = Optional.empty();
        if (isAvailable(dto.getDrinks())) {
            var order = Order.builder()
                    .creationTime(dto.getCreationTime())
                    .drinks(dto.getDrinks())
                    .number(sequenceRepository.findFirstByOrderByCountAsc()+1L)
                    .table(dto.getTable())
                    .check(getCheck(dto.getDrinks()))
                    .status(Status.New)
                    .build();
            sequenceRepository.incrementCount();
            result = Optional.of(orderRepository.save(order));
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
