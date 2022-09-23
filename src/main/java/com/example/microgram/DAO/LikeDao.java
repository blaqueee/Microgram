package com.example.microgram.DAO;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;

@Component
@RequiredArgsConstructor
public class LikeDao {
    private final JdbcTemplate jdbcTemplate;

    public String ifUserLiked(String username, int postID) {
        String query = "select count(l.id) from likes l\n" +
                "inner join users u on u.id = l.user_id\n" +
                "where u.username = ? and l.post_id = ?";
        var result = jdbcTemplate.queryForObject(query, Integer.class, new Object[]{username, postID});
        return result != 0 ? "Пользователь " + username + " лайкал этот пост!" : "Пользователь " + username + " не лайкал этот пост!";
    }
}
