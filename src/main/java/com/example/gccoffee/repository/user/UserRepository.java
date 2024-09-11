package com.example.gccoffee.repository.user;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;
import com.example.gccoffee.model.user.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    Optional<User> findByEmail(Email email);
    List<User> findAll();
    User save(User user);
    void update(Password password, Email email);
    void delete(Email email);
}
