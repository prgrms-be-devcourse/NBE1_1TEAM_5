package com.example.gccoffee.repository.user;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Name;
import com.example.gccoffee.model.Password;
import com.example.gccoffee.model.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                new Email(rs.getString("email")),  // Email 객체로 변환
                new Name(rs.getString("name")),
                new Password(rs.getString("password"))
        );
    }
}

