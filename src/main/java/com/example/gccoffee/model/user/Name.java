package com.example.gccoffee.model.user;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.regex.Pattern;

@Getter
public class Name {

    private final String value;

    public Name(String value) {
        Assert.notNull(value, "이름은 null일 수 없습니다.");
        Assert.isTrue(value.length() >= 2 && value.length() <= 10, "이름은 2자 이상, 10자 이하이어야 합니다.");
        Assert.isTrue(isValidName(value), "이름에는 문자, 공백, 하이픈(-), 또는 작은따옴표(')만 포함될 수 있습니다.");
        this.value = value;
    }

    private static boolean isValidName(String value) {
        // 허용된 문자: 알파벳, 공백, 하이픈(-), 작은따옴표(')
        return Pattern.matches("^[a-zA-Z\\s'-]+$", value);
    }
}
