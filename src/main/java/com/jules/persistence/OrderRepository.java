package com.jules.persistence;

import com.jules.models.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends MongoRepository<Order, Long>, OrderRepositoryCustom {
}
