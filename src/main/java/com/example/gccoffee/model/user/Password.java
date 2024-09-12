package com.example.gccoffee.model.user;

import com.example.gccoffee.model.Email;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public class Password {

    private final String value;

    public Password(String value) {
        Assert.notNull(value, "Password should not be null");
        Assert.isTrue(value.length() >= 8 && value.length() <= 50, "Password length must be between 8 and 50 characters.");
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
