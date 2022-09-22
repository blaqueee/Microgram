package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

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

    public String isRegistered(String email) {
        var user = Optional.of(getUserByEmail(email));
        return user.isPresent() ? "Пользователь есть в системе" : "Пользователя нету в системе";
    }
}
