package com.example.gccoffee.controller;

import com.example.gccoffee.model.Password;
import org.springframework.security.core.userdetails.User;

public record UserUpdateRequest(
        Password password
) {
    public static UserUpdateRequest of(Password password) {
        return new UserUpdateRequest(password);
    }

}
