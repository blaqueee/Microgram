package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final String queryTemp = "select u.id,\n" +
            "       u.username,\n" +
            "       u.name,\n" +
            "       u.email,\n" +
            "       u.password,\n" +
            "       (select count(id)\n" +
            "           from posts p\n" +
            "           where p.user_id = u.id) posts,\n" +
            "       (select count(id)\n" +
            "        from subscriptions s\n" +
            "        where s.follower_id = u.id) subscriptions,\n" +
            "       (select count(id)\n" +
            "           from subscriptions s\n" +
            "           where s.user_id = u.id) followers \n";

    public List<UserDto> getUserByName(String name) {
        String query = queryTemp + "from users u where u.name = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserDto.class), name);
    }

    public List<UserDto> getUserByUsername(String username) {
        String query = queryTemp + "from users u where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserDto.class), username);
    }

    public List<UserDto> getUserByEmail(String email) {
        String query =  queryTemp + "from users u where u.email = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserDto.class), email);
    }

    public String ifExists(String email) {
        String query = "select count(id) from users\n" +
                "where email = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, email);
        return result == 1 ? "Пользователь есть в системе" : "Пользователя нету в системе";
    }

    public List<UserDto> getFollowersByUsername(String username) {
        String query = queryTemp + "from subscriptions s\n" +
                "         inner join users u on u.id = s.follower_id\n" +
                "where s.user_id = (\n" +
                "    select id\n" +
                "    from users\n" +
                "    where username = ?\n" +
                ");";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserDto.class), username);
    }

    public List<UserDto> getSubscriptionsByUsername(String username) {
        String query = queryTemp + "from subscriptions s\n" +
                "         inner join users u on u.id = s.user_id\n" +
                "where s.follower_id = (\n" +
                "    select id\n" +
                "    from users\n" +
                "    where username = ?\n" +
                ");";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(UserDto.class), username);
    }
}