package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import com.example.microgram.Entity.User;
import com.example.microgram.Utility.DataGenerator.UserEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

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
        String query = queryTemp + "from users u where u.email = ?";
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

    public void dropTableAuthorities() {
        String query = "DROP TABLE IF EXISTS authorities";
        jdbcTemplate.execute(query);
        System.out.println("dropped table 'authorities'");
    }

    public void createTableUsers() {
        String query = "create table users\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    username varchar(20) not null,\n" +
                "    name varchar(20) not null,\n" +
                "    email varchar(45) not null,\n" +
                "    password varchar not null," +
                "    enabled boolean not null\n" +
                ");\n";
        jdbcTemplate.update(query);
        System.out.println("created table 'users'");
    }

    public void createTableAuthorities() {
        String query = "CREATE TABLE authorities\n" +
                "(\n" +
                "    id serial primary key not null,\n" +
                "    user_id integer not null references users (id),\n" +
                "    authority text not null\n" +
                ");";
        jdbcTemplate.update(query);
        System.out.println("created table 'authorities'");
    }

    public void insertUsers(List<UserEnum> users) {
        String query = "INSERT INTO users(username, name, email, password, enabled)\n" +
                "VALUES(?, ?, ?, ?, ?)";
        String queryAuth = "INSERT INTO authorities(user_id, authority)" +
                "VALUES(?, ?)";
        for (int i = 0; i < users.size(); i++) {
            int finalI = i;
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, users.get(finalI).getUsername());
                ps.setString(2, users.get(finalI).getName());
                ps.setString(3, users.get(finalI).getEmail());
                ps.setString(4, encoder.encode(users.get(finalI).getPassword()));
                ps.setBoolean(5, users.get(finalI).isEnabled());
                return ps;
            });
            jdbcTemplate.update(queryAuth, finalI + 1, "ROLE_USER");
        }
        System.out.println("inserted " + users.size() + " rows into 'users'");
    }

    public Long createNewUser(User user) {
        String query = "INSERT INTO users(username, name, email, password, enabled)\n" +
                "VALUES(?, ?, ?, ?, true)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(query, new String[]{"id"});
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, encoder.encode(user.getPassword()));
            return ps;
        }, keyHolder);
        createAuthority(user.getUsername());
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    private void createAuthority(String username) {
        var userID = getIdByUsername(username);
        String query = "INSERT INTO authorities(user_id, authority)" +
                "VALUES(?, 'ROLE_USER')";
        jdbcTemplate.update(query, userID);
    }

    public boolean ifExistsUsername(String username) {
        String query = "select count(id) from users\n" +
                "where username = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, username);
        return result == 1;
    }

    public Long getIdByUsername(String username) {
        String query = "SELECT id FROM users WHERE username = ?";
        return jdbcTemplate.queryForObject(query, Long.class, username);
    }

    public boolean ifExistsId(Long id) {
        String query = "SELECT COUNT(*) FROM users WHERE id = ?";
        var count = jdbcTemplate.queryForObject(query, Integer.class, id);
        return count == 1;
    }

    public Optional<UserDto> getUserDtoById(Long id) {
        String query = "SELECT * FROM users WHERE id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(UserDto.class), id));
    }

    public boolean ifExists(User user) {
        if (ifExistsEmail(user.getEmail()))
            return true;
        if (ifExistsUsername(user.getUsername()))
            return true;
        return false;
    }
}