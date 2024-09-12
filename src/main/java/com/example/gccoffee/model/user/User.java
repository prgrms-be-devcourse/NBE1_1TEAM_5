package com.example.gccoffee.model.user;

import com.example.gccoffee.controller.request.SignUpRequest;
import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Email email;
    private Password password;
    private Name name;

    public User(Email email, Name name, Password password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
    public static User from(SignUpRequest request) {
        return new User(request.email(), request.name(), request.password());
    }
}
