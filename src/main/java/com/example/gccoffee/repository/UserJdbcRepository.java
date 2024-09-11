package com.example.gccoffee.repository;


import com.example.gccoffee.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class UserJdbcRepository implements UserRepository {

    private final JdbcTemplate jdbcTemplate;


    @Override
    public User findById(Long id) {
        String sql = "select * from users where id = ?";

        String sql = "SELECT name FROM users WHERE id = ?";
        return jdbcTemplate.query(sql, new Object[]{id}, new RowMapper<User>() {});
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public User save(User user) {
        return null;
    }

    @Override
    public User update(User user, Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
