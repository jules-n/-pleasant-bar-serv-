package com.jules.persistence;

import com.jules.dtos.OrderStatusDTO;
import com.jules.models.Order;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class OrderRepositoryCustomImpl implements OrderRepositoryCustom {

    @Setter(onMethod_ = @Autowired)
    private MongoTemplate mongoTemplate;

    @Override
    public boolean updateStatus(Order order) {
        var update = new Update();
        var criteria = Criteria.where("number").is(order.getNumber());
        update.set("status", order.getStatus());
        var result = mongoTemplate.updateFirst(new Query(criteria), update, Order.COLLECTION_NAME);
        return result.wasAcknowledged();
    }
}
