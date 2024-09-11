package com.example.gccoffee.model;

import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

public class Password {

    private final String value;

    public Password(String value) {
        Assert.notNull(value, "Password should not be null");
        Assert.isTrue(value.length() >= 8 && value.length() <= 50, "Password length must be between 8 and 50 characters.");
        Assert.isTrue(checkPassword(value), "Password does not meet the required format.");
        this.value = value;
    }

    private static boolean checkPassword(String value) {
        // Example: Password must contain at least one digit, one lower case letter, one upper case letter, and one special character.
        return Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*]).{8,50}$", value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password = (Password) o;
        return Objects.equals(value, password.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Password{");
        sb.append("value='").append("******").append('\'');  // Masked for security reasons
        sb.append('}');
        return sb.toString();
    }

    public String getValue() {
        return value;
    }
}
