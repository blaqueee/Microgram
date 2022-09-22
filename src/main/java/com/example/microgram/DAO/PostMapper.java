package com.example.microgram.DAO;

import com.example.microgram.DTO.PostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class PostMapper implements RowMapper<PostDto> {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public PostDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        int postID = rs.getInt("id");
        return PostDto.builder()
                .image(rs.getString("image"))
                .description(rs.getString("description"))
                .time(rs.getObject("time", LocalDateTime.class))
                .comments(getComments(postID))
                .build();
    }

    private Integer getComments(int postID) {
        String query = "select count(id)\n" +
                "from comments\n" +
                "where post_id = ?";
        return jdbcTemplate.queryForObject(query, Integer.class, postID);
    }
}
