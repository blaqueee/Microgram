package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public List<PostDto> getPostsByUsername(String username) {
        String query = "select p.image, p.description, p.time, " +
                "(select count(id)\n" +
                "from comments c where c.post_id = p.id) comments\n" +
                "from posts p\n" +
                "inner join users u on p.user_id = u.id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class), username);
    }

    public List<PostDto> getReelsByUsername(String username) {
        String query = "select p.image, p.description, p.time,\n" +
                "(select count(id)\n" +
                "from comments c where c.post_id = p.id) comments\n" +
                "from posts p\n" +
                "inner join subscriptions s on s.user_id = p.user_id\n" +
                "inner join users u on u.id = s.follower_id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(PostDto.class), username);
    }
}
