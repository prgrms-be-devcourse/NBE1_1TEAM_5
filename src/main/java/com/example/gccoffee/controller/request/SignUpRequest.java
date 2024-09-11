package com.example.gccoffee.controller.request;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Name;
import com.example.gccoffee.model.Password;

public record SignUpRequest(
        Email email,
        Password password,
        Name name
) {
    public static SignUpRequest of(Email email, Password password, Name name) {
        return new SignUpRequest(email, password, name);
    }
}
