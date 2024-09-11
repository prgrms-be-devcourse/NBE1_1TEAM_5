package com.example.gccoffee.repository;

import com.example.gccoffee.model.User;

import java.util.List;

public interface UserRepository {


    User findById(Long id);
    List<User> findAll();
    User save(User user);
    User update(User user, Long id);
    void delete(Long id);
}
