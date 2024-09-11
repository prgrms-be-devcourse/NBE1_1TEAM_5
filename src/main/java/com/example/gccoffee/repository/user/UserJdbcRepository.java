package com.example.gccoffee.repository.user;


import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.Password;
import com.example.gccoffee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserJdbcRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public User findByEmail(Email email) {
        String sql = "SELECT email, password, name FROM users WHERE email = :email";
        Map<String, Object> param = Map.of("email", email);
        return jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT email, password, name FROM users";
        return jdbcTemplate.query(sql, new UserRowMapper());
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO user(email, password, name) " +
                "VALUES (:email, :password, :name)";
        jdbcTemplate.update(sql, toUserParamMap(user));
        return user;
    }

    @Override
    public User update(Password password, Email email) {
        Map<String, Object> param = Map.of("password", password);
        String sql = "UPDATE users SET password = :password WHERE email = :email";
        jdbcTemplate.update(sql, param);
        return findByEmail(email);
    }

    @Override
    public void delete(Email email) {
        String sql = "DELETE FROM users WHERE email = :email";
        Map<String, Object> param = Map.of("email", email.getAddress());
        jdbcTemplate.update(sql, param);
    }

    private Map<String, Object> toUserParamMap(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", user.getEmail().getAddress());
        paramMap.put("password", user.getPassword().getValue());
        paramMap.put("name", user.getName().getValue());
        return paramMap;
    }
}
