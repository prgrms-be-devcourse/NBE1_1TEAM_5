package com.example.gccoffee.controller;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Password;

public record LoginRequest(
        Email email,
        Password password
) {
    public static LoginRequest of(Email email, Password password) {
        return new LoginRequest(email, password);
    }
}
