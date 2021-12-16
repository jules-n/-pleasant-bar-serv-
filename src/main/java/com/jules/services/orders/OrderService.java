package com.jules.services.orders;

import com.jules.dtos.OrderPostDTO;
import com.jules.dtos.OrderStatusDTO;
import com.jules.models.Order;

import java.util.Optional;

public interface OrderService {
    Optional<Order> save(OrderPostDTO dto);
    boolean updateStatus(OrderStatusDTO dto);

}
