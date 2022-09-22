package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;

    public UserDto getUserByName(String name) {
        String query = "select * from users where name = ?";
        return jdbcTemplate.queryForObject(query, new UserMapper(jdbcTemplate), name);
    }

    public UserDto getUserByUsername(String username) {
        String query = "select * from users where username = ?";
        return jdbcTemplate.queryForObject(query, new UserMapper(jdbcTemplate), username);
    }

    public UserDto getUserByEmail(String email) {
        String query = "select * from users where email = ?";
        return jdbcTemplate.queryForObject(query, new UserMapper(jdbcTemplate), email);
    }

    public String ifExists(String email) {
        String query = "select count(id) from users\n" +
                "where email = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, email);
        return result == 1 ? "Пользователь есть в системе" : "Пользователя нету в системе";
    }
}
