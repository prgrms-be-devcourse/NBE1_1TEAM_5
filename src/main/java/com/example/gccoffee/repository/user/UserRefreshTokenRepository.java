package com.example.gccoffee.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class UserRefreshTokenRepository {

    private final NamedParameterJdbcTemplate template;


}
