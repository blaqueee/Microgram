package com.example.microgram.DAO;

import com.example.microgram.DTO.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@RequiredArgsConstructor
public class UserMapper implements RowMapper<UserDto> {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public UserDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int userID = rs.getInt("id");

        return UserDto.builder()
                .userName(rs.getString("username"))
                .name(rs.getString("name"))
                .email(rs.getString("email"))
                .password(rs.getString("password"))
                .posts(getAmountOfPosts(userID))
                .subscriptions(getAmountOfSubs(userID))
                .followers(getAmountOfFollowers(userID))
                .build();
    }

    private Integer getAmountOfPosts(int id) {
        String query = "select count(id)\n" +
                "from posts\n" +
                "where user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
    }

    private Integer getAmountOfSubs(int id) {
        String query = "select count(user_id)\n" +
                "from subscriptions\n" +
                "where follower_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
    }

    private Integer getAmountOfFollowers(int id) {
        String query = "select count(follower_id)\n" +
                "from subscriptions\n" +
                "where user_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, id);
    }
}
