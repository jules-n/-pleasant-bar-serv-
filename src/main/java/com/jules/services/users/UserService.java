package com.jules.services.users;

import com.jules.models.User;

import java.util.Optional;

public interface UserService {
    boolean save(User user);
    boolean addRights(String username, String role);
    boolean update(String username, User user);
    Optional<User> findByUsername(String username);
}
