package com.example.gccoffee.repository;


import com.example.gccoffee.model.Order;
import com.example.gccoffee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserRowMapper userRowMapper;


    @Override
    public User findById(Long id) {
        String sql = "SELECT name FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id} , User.class);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, userRowMapper);
    }

    @Override
    public User save(User user) {
        String sql = "INSERT INTO user(id, email, password, name) " +
                "VALUES (:id, :email, :password, :name)";
        return jdbcTemplate.update(sql, toUserParamMap(user));
    }

    @Override
    public User update(User user, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    private Map<String, Object> toUserParamMap(User user) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", user.getId());
        paramMap.put("email", user.getEmail().getAddress());
        paramMap.put("password", user.getPassword().getValue());
        paramMap.put("name", user.getName().getValue());
        return paramMap;
    }
}
