package com.jules.persistence;

import com.jules.models.User;

public interface UserRepositoryCustom {
    boolean changeRole(String username, String role);
    boolean updateUser(String oldUsername, User user);
}
