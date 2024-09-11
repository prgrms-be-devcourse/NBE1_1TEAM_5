package com.example.gccoffee.repository.user;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Password;
import com.example.gccoffee.model.User;

import java.util.List;

public interface UserRepository {

    User findByEmail(Email email);
    List<User> findAll();
    User save(User user);
    User update(Password password, Email email);
    void delete(Email email);
}
