package com.jules.persistence;

import com.jules.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSecurityRepository extends MongoRepository<User, String> {
    User findByUsername(String username);
}
