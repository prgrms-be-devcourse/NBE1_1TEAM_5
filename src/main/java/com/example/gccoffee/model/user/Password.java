package com.example.gccoffee.model.user;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Password {

    private final String value;

    public Password(String value) {
        Assert.notNull(value, "Password should not be null");
        Assert.isTrue(value.length() >= 8 && value.length() <= 50, "Password length must be between 8 and 50 characters.");
        this.value = value;
    }

}
