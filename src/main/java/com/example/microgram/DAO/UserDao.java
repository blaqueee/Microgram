package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import com.example.microgram.Utility.DataGenerator.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
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

    public boolean ifExistsEmail(String email) {
        String query = "select count(id) from users\n" +
                "where email = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, email);
        return result == 1;
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

    public void dropTable() {
        String query = "DROP TABLE IF EXISTS users";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'users'");
    }

    public void createTableUsers() {
        String query = "create table users\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    username varchar(20) not null,\n" +
                "    name varchar(20) not null,\n" +
                "    email varchar(45) not null,\n" +
                "    password varchar(45) not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'users'");
    }

    public void insertUsers(List<UserEnum> users) {
        String query = "INSERT INTO users(username, name, email, password)\n" +
                "VALUES(?, ?, ?, ?)";
        for (UserEnum user : users) {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, user.getUsername());
                ps.setString(2, user.getName());
                ps.setString(3, user.getEmail());
                ps.setString(4, user.getPassword());
                return ps;
            });
        }
        System.out.println("inserted " + users.size() + " rows into 'users'");
    }

    public String createNewUser(User user) {
        if (!ifExists(user)) {
            String query = "INSERT INTO users(username, name, email, password)\n" +
                    "VALUES(?, ?, ?, ?)";
            var sm = jdbcTemplate.update(query,
                    user.getUsername(), user.getName(), user.getEmail(), user.getPassword());
            return "Регистрация прошла успешно!";
        }
        return "Не удалось завершить регистрацию!\n" +
                "Такое имя пользователя или электронная почта существует!";
    }

    public boolean loginByUsername(User user) {
        if (ifExistsUsername(user.getUsername())) {
            var userDto = getUserByUsername(user.getUsername()).get(0);
            if (userDto.getPassword().equals(user.getPassword()))
                return true;
        }
        return false;
    }

    public boolean ifExistsUsername(String username) {
        String query = "select count(id) from users\n" +
                "where username = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, username);
        return result == 1;
    }

    private boolean ifExists(User user) {
        if (ifExistsEmail(user.getEmail()))
            return true;
        if (ifExistsUsername(user.getUsername()))
            return true;
        return false;
    }
}