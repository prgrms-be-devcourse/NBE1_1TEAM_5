package com.example.gccoffee.controller;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Name;
import com.example.gccoffee.model.User;
import lombok.Builder;

@Builder
public record UserResponse(
        Email email,
        Name name
) {
    public static UserResponse from(User user) {
        return new UserResponse(user.getEmail(), user.getName());
    }

    public static UserResponse of(Email email, Name name) {
        return new UserResponse(email, name);
    }
}
