package com.example.gccoffee.repository.user;


import com.example.gccoffee.model.Email;
import com.example.gccoffee.model.user.Password;
import com.example.gccoffee.model.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserJdbcRepository implements UserRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Optional<User> findByEmail(Email email) {
        String sql = "SELECT email, password, name FROM users WHERE email = :email";
        try {
            Map<String, Object> param = Map.of("email", email);
            User user = jdbcTemplate.queryForObject(sql, param, new UserRowMapper());
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
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
    public void update(Password password, Email email) {
        String sql = "UPDATE users SET password = :password WHERE email = :email";
        jdbcTemplate.update(sql, toUserParamMap(email, password));
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

    private Map<String, Object> toUserParamMap(Email email, Password password) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("email", email.getAddress());
        paramMap.put("password", password.getValue());
        return paramMap;
    }
}
