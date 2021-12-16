package com.jules.persistence;

import com.jules.models.Drink;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Optional;

public class DrinkRepositoryCustomImpl implements DrinkRepositoryCustom {

    @Setter(onMethod_ = @Autowired)
    private MongoTemplate mongoTemplate;

    @Override
    public boolean update(String name, Drink drink) {
        var update = new Update();
        var criteria = Criteria.where("name").is(name);
        update.set("amount",drink.getAmount());
        update.set("totalAmount",drink.getTotalAmount());
        update.set("characteristics",drink.getCharacteristics());
        update.set("components",drink.getComponents());
        update.set("price",drink.getPrice());
        update.set("name",drink.getName());
        var result = mongoTemplate.updateFirst(new Query(criteria), update, Drink.COLLECTION_NAME);
        return result.wasAcknowledged();
    }

    @Override
    public boolean deleteByName(String name) {
        var criteria = Criteria.where("name").is(name);
        return mongoTemplate.remove(new Query(criteria), Drink.class).wasAcknowledged();
    }

    @Override
    public Optional<Drink> findDrinkByName(String name) {
        var criteria = Criteria.where("name").is(name);
        var drink = mongoTemplate.findOne(new Query(criteria), Drink.class);
        return Optional.of(drink);
    }
}
