package com.jules.services.users;

import com.jules.models.User;
import com.jules.persistence.UserRepository;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Setter(onMethod_ = {@Autowired})
    private UserRepository repository;

    @Setter(onMethod_ = {@Autowired})
    private PasswordEncoder encoder;

    @Override
    public boolean save(User user) {
        user.setRole("waiter");
        user.setPassword(encoder.encode(user.getPassword()));
        var result = repository.save(user);
        return result!=null;
    }

    @Override
    public boolean addRights(@NonNull String username, String role) {
        return repository.changeRole(username, role);
    }

    @Override
    public boolean update(@NonNull String username, User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return repository.updateUser(username, user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.of(repository.findByUsername(username));
    }
}
