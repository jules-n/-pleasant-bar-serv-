package com.jules.persistence;

import com.jules.models.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private MongoTemplate mongoTemplate;

    public UserRepositoryCustomImpl(MongoTemplate mongoTemplate){
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean changeRole(String username, String role) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));
        var update = new Update();
        update.set("role", role);
        var result = mongoTemplate.updateFirst(query, update, User.COLLECTION_NAME);
        return result.wasAcknowledged();
    }

    @Override
    public boolean updateUser(String oldUsername, User user) {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(oldUsername));
        var update = new Update();
        update.set("username", user.getUsername());
        update.set("password", user.getPassword());
        var result = mongoTemplate.updateFirst(query, update, User.COLLECTION_NAME);
        return result.wasAcknowledged();
    }
}
