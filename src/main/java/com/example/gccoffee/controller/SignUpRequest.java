package com.example.gccoffee.controller;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Name;
import com.example.gccoffee.model.Password;
import org.springframework.security.core.userdetails.User;

public record SignUpRequest(
        Email email,
        Password password,
        Name name
) {
    public static SignUpRequest of(Email email, Password password, Name name) {
        return new SignUpRequest(email, password, name);
    }
}
