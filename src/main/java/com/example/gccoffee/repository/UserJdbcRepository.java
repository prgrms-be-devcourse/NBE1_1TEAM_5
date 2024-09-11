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
        String sql = "SELECT name FROM users WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id} , User.class);
    }

    @Override
    public List<User> findAll() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            new User(
                    rs.getLong("id"),
                    rs.ge
            )
        })
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
