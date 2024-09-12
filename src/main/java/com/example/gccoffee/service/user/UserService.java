package com.example.gccoffee.service.user;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;
import com.example.gccoffee.model.user.User;

import java.util.List;

public interface UserService {

    List<User> readAll();
    User readDetail(Email email);
    User signUp(User user);
    String login(Email email, Password password);
    void edit(Email email, Password password);
    void delete(Email email);
}
