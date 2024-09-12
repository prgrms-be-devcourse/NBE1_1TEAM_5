package com.example.gccoffee.controller.request;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;

public record LoginRequest(
        Email email,
        Password password
) {
    public static LoginRequest of(Email email, Password password) {
        return new LoginRequest(email, password);
    }
}
