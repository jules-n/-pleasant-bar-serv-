package com.jules.persistence;

import com.jules.models.Order;

public interface OrderRepositoryCustom {
    boolean updateStatus(Order order);
}
