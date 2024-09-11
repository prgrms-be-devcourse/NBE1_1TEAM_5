package com.example.gccoffee.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Long id;
    private Email email;
    private Password password;
    private Name name;

    public User(Long id, Email email, Name name, Password password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
