package com.example.gccoffee.repository;

import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Name;
import com.example.gccoffee.model.Password;
import com.example.gccoffee.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {


    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        return new User(
                rs.getLong("id"),  // UUID로 변환
                new Email(rs.getString("email")),  // Email 객체로 변환
                new Name(rs.getString("name")),
                new Password(rs.getString("password"))
        );
    }
}

