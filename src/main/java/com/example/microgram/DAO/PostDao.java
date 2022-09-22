package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostDao {
    private final JdbcTemplate jdbcTemplate;

    public List<PostDto> getPostsByUsername(String username) {
        String query = "select * from posts p\n" +
                "inner join users u on p.user_id = u.id\n" +
                "where u.username = ?";
        return jdbcTemplate.query(query, new PostMapper(jdbcTemplate), username);
    }
}
