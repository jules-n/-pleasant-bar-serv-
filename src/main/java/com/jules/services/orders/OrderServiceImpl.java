package com.jules.services.orders;

import com.jules.persistence.OrderRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    @Setter(onMethod_ = {@Autowired})
    private OrderRepository repository;
}
